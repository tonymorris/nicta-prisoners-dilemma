package com.nicta.etd.prisonersdilemma;

public enum Player {
  Player1, Player2;

  public boolean isPlayer1() {
    return this == Player1;
  }

  public boolean isPlayer2() {
    return this == Player2;
  }

  public Player invert() {
    return this == Player1 ? Player2 : Player1;
  }
}
