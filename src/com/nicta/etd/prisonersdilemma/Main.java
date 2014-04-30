package com.nicta.etd.prisonersdilemma;

import static com.nicta.etd.prisonersdilemma.Play.playIt;
import static com.nicta.etd.prisonersdilemma.Strategy.*;

public final class Main {
  public static void main(String[] args) {
    final Result<Integer> q1 = playIt(1000, random, titForTat);
    System.out.println(q1);
    final Result<Integer> q2 = playIt(1000, alwaysDefect, alwaysDefect);
    System.out.println(q2);
  }
}
