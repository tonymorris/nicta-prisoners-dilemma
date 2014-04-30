package com.nicta.etd.prisonersdilemma;

import fj.data.List;
import fj.data.Option;

public final class ChoiceHistory {
  private final List<Turn> list;

  public ChoiceHistory(final List<Turn> list) {
    this.list = list;
  }

  public Option<Turn> previousTurn() {
    return list.toOption();
  }

  public ChoiceHistory add(final Turn t) {
    return new ChoiceHistory(list.cons(t));
  }
}
