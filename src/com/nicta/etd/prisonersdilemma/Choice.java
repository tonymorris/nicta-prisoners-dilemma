package com.nicta.etd.prisonersdilemma;

public enum Choice {
  Cooperate, Defect;

  public boolean isCooperate() {
    return this == Cooperate;
  }

  public boolean isDefect() {
    return this == Defect;
  }

  public Choice invert() {
    return this == Cooperate ? Defect : Cooperate;
  }
}
