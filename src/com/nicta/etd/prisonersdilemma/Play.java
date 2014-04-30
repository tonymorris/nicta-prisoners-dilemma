package com.nicta.etd.prisonersdilemma;

import fj.*;

import static com.nicta.etd.prisonersdilemma.ChoiceState.choiceState;
import static com.nicta.etd.prisonersdilemma.Player.Player1;
import static com.nicta.etd.prisonersdilemma.Player.Player2;
import static com.nicta.etd.prisonersdilemma.Turn.turn;
import static fj.P.p;

public abstract class Play<C, A> {
  abstract ChoiceState<A> run(Scoring<C> s, Strategy<Choice> player1, Strategy<Choice> player2);

  public <B> Play<C, B> map(final F<A, B> f) {
    return play((s, m, t) -> run(s, m, t).map(f));
  }

  public <B> Play<C, B> bind(final F<A, Play<C, B>> f) {
    return play((s, m, t) -> run(s, m, t).bind(a -> f.f(a).run(s, m, t)));
  }

  public static <C, A> Play<C, A> insert(final A a) {
    return play((s, m, t) -> ChoiceState.insert(a));
  }

  public static <C, A> Play<C, A> play(final F3<Scoring<C>, Strategy<Choice>, Strategy<Choice>, ChoiceState<A>> f) {
    return new Play<C, A>() {
      ChoiceState<A> run(final Scoring<C> s, final Strategy<Choice> player1, final Strategy<Choice> player2) {
        return f.f(s, player1, player2);
      }
    };
  }

  public static <T> Play<T, Result<T>> game() {
    return play((s, p1, p2) -> choiceState(h -> {
      final Choice player1Choice = p1.run(h, Player1);
      final Choice player2Choice = p2.run(h, Player2);
      final Turn u = turn(player1Choice, player2Choice);
      return p(h.add(u), s.score(u));
    }));
  }

  public static <T> Play<T, Result<T>> game(final int n, final Monoid<T> m) {
    return n <= 0 ?
        insert(Result.empty(m))
        : Play.<T>game().bind(r -> game(n - 1, m).map(s -> r.add(s, m.semigroup())));
  }

  public static Play<Integer, Result<Integer>> game(final int n) {
    return game(n, Monoid.intAdditionMonoid);
  }

  public static <A> A playIt(final Play<Integer, A> p, final Strategy<Choice> player1, final Strategy<Choice> player2) {
    return p.run(Scoring.defaultScoring, player1, player2).evalStart();
  }

  public static Result<Integer> playIt(final int n, final Strategy<Choice> player1, final Strategy<Choice> player2) {
    return Play.playIt(Play.game(n), player1, player2);
  }

}
