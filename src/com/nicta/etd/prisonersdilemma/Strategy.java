package com.nicta.etd.prisonersdilemma;

import fj.F;

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
}
