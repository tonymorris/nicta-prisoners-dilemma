package com.nicta.etd.prisonersdilemma;

import fj.F;
import fj.P;
import fj.P2;

import static fj.P.p;

public abstract class ChoiceState<A> {
  public abstract P2<ChoiceHistory, A> run(ChoiceHistory h);

  public A eval(final ChoiceHistory h) {
    return run(h)._2();
  }

  public ChoiceHistory exec(final ChoiceHistory h) {
    return run(h)._1();
  }

  public <B> ChoiceState<B> map(final F<A, B> f) {
    return choiceState(h -> run(h).map2(f));
  }

  public <B> ChoiceState<B> bind(final F<A, ChoiceState<B>> f) {
    return choiceState(h -> {
      P2<ChoiceHistory, A> p = run(h);
      return f.f(p._2()).run(p._1());
    });
  }

  public static <A> ChoiceState<A> insert(final A a) {
    return choiceState(h -> p(h, a));
  }

  public static <A> ChoiceState<A> choiceState(final F<ChoiceHistory, P2<ChoiceHistory, A>> f) {
    return new ChoiceState<A>() {
      public P2<ChoiceHistory, A> run(final ChoiceHistory h) {
        return f.f(h);
      }
    };
  }

  public static <A> ChoiceState<A> choiceTurn(final F<ChoiceHistory, P2<Turn, A>> f) {
    return choiceState(h -> f.f(h).map1(h::add));
  }
}
