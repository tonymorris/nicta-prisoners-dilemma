package com.nicta.etd.prisonersdilemma;

import static com.nicta.etd.prisonersdilemma.Choice.Cooperate;
import static com.nicta.etd.prisonersdilemma.Choice.Defect;

public final class Turn {
  private final Choice mine;
  private final Choice theirs;

  public Turn(final Choice mine, final Choice theirs) {
    this.mine = mine;
    this.theirs = theirs;
  }

  public Choice mine() {
    return mine;
  }

  public Choice theirs() {
    return theirs;
  }

  public Turn setMine(final Choice c) {
    return new Turn(c, theirs);
  }

  public Turn setTheirs(final Choice c) {
    return new Turn(mine, c);
  }

  public Turn invertMine() {
    return setMine(mine.invert());
  }

  public Turn invertTheirs() {
    return setTheirs(theirs.invert());
  }

  public Turn switchTurn() {
    return new Turn(theirs, mine);
  }

  public static final Turn bothCooperate =
      new Turn(Cooperate, Cooperate);

  public static final Turn bothDefect =
      new Turn(Defect, Defect);

  public static final Turn cooperateDefect =
      new Turn(Cooperate, Defect);

  public static final Turn defectCooperate =
      new Turn(Defect, Cooperate);
}
