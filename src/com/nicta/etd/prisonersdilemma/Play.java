package com.nicta.etd.prisonersdilemma;

import fj.F;
import fj.F3;

public abstract class Play<C, A> {
  abstract ChoiceState<A> run(Scoring<C> s, Strategy<Choice> mine, Strategy<Choice> theirs);

  public <B> Play<C, B> map(final F<A, B> f) {
    return play((s, m, t) -> run(s, m, t).map(f));
  }

  public <B> Play<C, B> bind(final F<A, Play<C, B>> f) {
    return play((s, m, t) -> run(s, m, t).bind(a -> f.f(a).run(s, m, t)));
  }

  public static <C, A> Play<C, A> play(final F3<Scoring<C>, Strategy<Choice>, Strategy<Choice>, ChoiceState<A>> f) {
    return new Play<C, A>() {
      ChoiceState<A> run(final Scoring<C> s, final Strategy<Choice> mine, final Strategy<Choice> theirs) {
        return f.f(s, mine, theirs);
      }
    };
  }
}
