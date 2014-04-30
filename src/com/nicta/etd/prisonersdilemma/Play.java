package com.nicta.etd.prisonersdilemma;

import fj.*;

import static com.nicta.etd.prisonersdilemma.ChoiceState.choiceState;
import static com.nicta.etd.prisonersdilemma.Turn.turn;

public abstract class Play<C, A> {
  abstract ChoiceState<A> run(Scoring<C> s, Strategy<Choice> mine, Strategy<Choice> theirs);

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
      ChoiceState<A> run(final Scoring<C> s, final Strategy<Choice> mine, final Strategy<Choice> theirs) {
        return f.f(s, mine, theirs);
      }
    };
  }

  public static <T> Play<T, Result<T>> game() {
    return play((s, m, t) -> choiceState(h -> {
      final Choice mineChoice = m.run(h);
      final Choice theirsChoice = t.run(h);
      final Turn u = turn(mineChoice, theirsChoice);
      return P.p(h.add(u), u.score(s));
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

  public static <A> A playIt(final Play<Integer, A> p, final Strategy<Choice> mine, final Strategy<Choice> theirs) {
    return p.run(Scoring.defaultScoring, mine, theirs).evalStart();
  }

  public static Result<Integer> playIt(final int n, final Strategy<Choice> mine, final Strategy<Choice> theirs) {
    return Play.playIt(Play.game(n), mine, theirs);
  }

}
