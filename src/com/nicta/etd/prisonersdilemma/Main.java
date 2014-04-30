package com.nicta.etd.prisonersdilemma;

import static com.nicta.etd.prisonersdilemma.Play.playIt;
import static com.nicta.etd.prisonersdilemma.Strategy.alwaysCooperate;
import static com.nicta.etd.prisonersdilemma.Strategy.alwaysDefect;
import static com.nicta.etd.prisonersdilemma.Strategy.titForTat;

public final class Main {
  public static void main(String[] args) {
    final Result<Integer> q1 = playIt(10, alwaysCooperate, titForTat);
    System.out.println(q1);
    final Result<Integer> q2 = playIt(10, alwaysDefect, alwaysDefect);
    System.out.println(q2);
  }
}
