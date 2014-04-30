package com.nicta.etd.prisonersdilemma;

import fj.F;

import static com.nicta.etd.prisonersdilemma.Choice.Cooperate;
import static com.nicta.etd.prisonersdilemma.Choice.Defect;
import static com.nicta.etd.prisonersdilemma.Choice.random;

public abstract class Strategy<A> {
  public abstract A run(ChoiceHistory h);

  public <B> Strategy<B> map(final F<A, B> f) {
    return strategy(h -> f.f(run(h)));
  }

  public <B> Strategy<B> bind(final F<A, Strategy<B>> f) {
    return strategy(h -> f.f(run(h)).run(h));
  }

  public static <A> Strategy<A> strategy(final F<ChoiceHistory, A> f) {
    return new Strategy<A>() {
      public A run(final ChoiceHistory h) {
        return f.f(h);
      }
    };
  }

  public final static Strategy<Choice> alwaysDefect =
      constant(Defect);

  public final static Strategy<Choice> alwaysCooperate =
      constant(Cooperate);

  // We just used a variable. DANGER.
  public final static Strategy<Choice> random =
      strategy(x -> random());

  public static <A> Strategy<A> constant(final A a) {
    return strategy(x -> a);
  }

  public static Strategy<Choice> usingLastTurn(final Choice c, final F<Turn, Choice> f) {
    return strategy(h -> h.previousTurn().option(c, f));
  }

  public final static Strategy<Choice> titForTat =
      usingLastTurn(Cooperate, Turn::theirs);

  public final static Strategy<Choice> spite =
      usingLastTurn(Cooperate, t -> t.mine().isDefect() || t.theirs().isDefect() ? Defect : Cooperate);
}
