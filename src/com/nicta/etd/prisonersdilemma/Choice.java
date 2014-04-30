package com.nicta.etd.prisonersdilemma;

import java.util.Random;

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

  public static Choice random(final Random r) {
    return r.nextBoolean() ? Cooperate : Defect;
  }

  public static Choice random() {
    return random(new Random());
  }
}
