package com.nicta.etd.prisonersdilemma;

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

  public static <A> Result<A> result(final A mine, final A theirs) {
    return new Result<A>(mine, theirs);
  }
}
