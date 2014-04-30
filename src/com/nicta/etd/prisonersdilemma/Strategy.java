package com.nicta.etd.prisonersdilemma;

import fj.F;
import fj.F2;

import static com.nicta.etd.prisonersdilemma.Choice.Cooperate;
import static com.nicta.etd.prisonersdilemma.Choice.Defect;
import static com.nicta.etd.prisonersdilemma.Choice.random;

public abstract class Strategy<A> {
  public abstract A run(ChoiceHistory h, Player p);

  public <B> Strategy<B> map(final F<A, B> f) {
    return strategy((h, p) -> f.f(run(h, p)));
  }

  public <B> Strategy<B> bind(final F<A, Strategy<B>> f) {
    return strategy((h, p) -> f.f(run(h, p)).run(h, p));
  }

  public static <A> Strategy<A> strategy(final F2<ChoiceHistory, Player, A> f) {
    return new Strategy<A>() {
      public A run(final ChoiceHistory h, final Player p) {
        return f.f(h, p);
      }
    };
  }

  public final static Strategy<Choice> alwaysDefect =
      constant(Defect);

  public final static Strategy<Choice> alwaysCooperate =
      constant(Cooperate);

  // We just used a variable. DANGER.
  public final static Strategy<Choice> random =
      strategy((h, p) -> random());

  public static <A> Strategy<A> constant(final A a) {
    return strategy((h, p) -> a);
  }

  public static Strategy<Choice> usingLastTurnPlayer(final Choice c, final F2<Turn, Player, Choice> f) {
    return strategy((h, p) -> h.previousTurn().option(c, t -> f.f(t, p)));
  }

  public static Strategy<Choice> usingLastTurn(final Choice c, final F<Turn, Choice> f) {
    return usingLastTurnPlayer(c, (t, p) -> f.f(t));
  }
  public final static Strategy<Choice> titForTat =
      usingLastTurnPlayer(Cooperate, (t, p) -> p.isPlayer1() ? t.player2() : t.player1());

  public final static Strategy<Choice> spite =
      usingLastTurn(Cooperate, t -> t.player1().isDefect() || t.player2().isDefect() ? Defect : Cooperate);
}
