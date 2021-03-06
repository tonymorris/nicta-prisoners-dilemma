package com.nicta.etd.prisonersdilemma;

import fj.F;

import static com.nicta.etd.prisonersdilemma.Result.result;

public final class Scoring<A> {
  private final A freedom; // 0
  private final A mutualCooperation; // 1
  private final A mutualDefection; // 3
  private final A duped; // 5

  public Scoring(final A freedom, final A mutualCooperation, final A mutualDefection, final A duped) {
    this.freedom = freedom;
    this.mutualCooperation = mutualCooperation;
    this.mutualDefection = mutualDefection;
    this.duped = duped;
  }

  public <B> Scoring<B> map(final F<A, B> f) {
    return new Scoring<B>(f.f(freedom), f.f(mutualCooperation), f.f(mutualDefection), f.f(duped));
  }

  public <B> Scoring<B> apply(final Scoring<F<A, B>> f) {
    return new Scoring<B>(f.freedom.f(freedom), f.mutualCooperation.f(mutualCooperation), f.mutualDefection.f(mutualDefection), f.duped.f(duped));
  }

  public A freedom() {
    return freedom;
  }

  public A mutualCooperation() {
    return mutualCooperation;
  }

  public A mutualDefection() {
    return mutualDefection;
  }

  public A duped() {
    return duped;
  }

  public Result<A> score(final Turn t) {
    return t.player1().isCooperate() && t.player2().isCooperate() ?
      result(mutualCooperation, mutualCooperation) :
           t.player1().isDefect() && t.player2().isDefect() ?
               result(mutualDefection, mutualDefection) :
                    t.player1().isCooperate() && t.player2().isDefect() ?
                        result(duped, freedom) :
                        result(freedom, duped);
  }


  public static <A> Scoring<A> single(final A a) {
    return new Scoring<A>(a, a, a, a);
  }

  public static final Scoring<Integer> defaultScoring =
      new Scoring<>(0, 1, 3, 5);
}
