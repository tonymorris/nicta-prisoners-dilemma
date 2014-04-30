package com.nicta.etd.prisonersdilemma;

import fj.Monoid;
import fj.Semigroup;

public final class Result<A> {
  private final A mine;
  private final A theirs;

  public Result(final A mine, final A theirs) {
    this.mine = mine;
    this.theirs = theirs;
  }

  public A mine() {
    return mine;
  }

  public A theirs() {
    return theirs;
  }

  public Result<A> add(final Result<A> r, final Semigroup<A> s) {
    return result(s.sum(mine, r.mine), s.sum(theirs, r.theirs));
  }

  public static <A> Result<A> empty(final Monoid<A> m) {
    return result(m.zero(), m.zero());
  }

  public static <A> Result<A> result(final A mine, final A theirs) {
    return new Result<A>(mine, theirs);
  }
}
