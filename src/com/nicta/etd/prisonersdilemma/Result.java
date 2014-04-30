package com.nicta.etd.prisonersdilemma;

import fj.Monoid;
import fj.Semigroup;

public final class Result<A> {
  private final A player1;
  private final A player2;

  public Result(final A player1, final A player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  public A player1() {
    return player1;
  }

  public A player2() {
    return player2;
  }

  public Result<A> add(final Result<A> r, final Semigroup<A> s) {
    return result(s.sum(player1, r.player1), s.sum(player2, r.player2));
  }

  public static <A> Result<A> empty(final Monoid<A> m) {
    return result(m.zero(), m.zero());
  }

  public static <A> Result<A> result(final A player1, final A player2) {
    return new Result<A>(player1, player2);
  }

  @Override
  public String toString() {
    return "Result " + player1 + " : " + player2;
  }
}
