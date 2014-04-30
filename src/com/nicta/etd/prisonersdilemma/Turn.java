package com.nicta.etd.prisonersdilemma;

import static com.nicta.etd.prisonersdilemma.Choice.Cooperate;
import static com.nicta.etd.prisonersdilemma.Choice.Defect;
import static com.nicta.etd.prisonersdilemma.Result.result;

public final class Turn {
  private final Choice player1;
  private final Choice player2;

  private Turn(final Choice player1, final Choice player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  public Choice player1() {
    return player1;
  }

  public Choice player2() {
    return player2;
  }

  public Turn setPlayer1(final Choice c) {
    return new Turn(c, player2);
  }

  public Turn setPlayer2(final Choice c) {
    return new Turn(player1, c);
  }

  public Turn invertPlayer1() {
    return setPlayer1(player1.invert());
  }

  public Turn invertPlayer2() {
    return setPlayer2(player2.invert());
  }

  public Turn switchTurn() {
    return new Turn(player2, player1);
  }

  public static final Turn bothCooperate =
      new Turn(Cooperate, Cooperate);

  public static final Turn bothDefect =
      new Turn(Defect, Defect);

  public static final Turn cooperateDefect =
      new Turn(Cooperate, Defect);

  public static final Turn defectCooperate =
      new Turn(Defect, Cooperate);

  public static Turn turn(final Choice player1, final Choice player2) {
    return new Turn(player1, player2);
  }

  @Override
  public String toString() {
    return "Turn(" + player1 + "," + player2 + ")";
  }
}
