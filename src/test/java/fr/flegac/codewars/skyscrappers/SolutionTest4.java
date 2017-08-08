package fr.flegac.codewars.skyscrappers;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SolutionTest4 {

  private static int clues[][] = {
    { 2, 2, 1, 3,
      2, 2, 3, 1,
      1, 2, 2, 3,
      3, 2, 1, 3 },
    { 0, 0, 1, 2,
      0, 2, 0, 0,
      0, 3, 0, 0,
      0, 1, 0, 0 }
  };

  private static int outcomes[][][] = {
    { { 1, 3, 4, 2 },
      { 4, 2, 1, 3 },
      { 3, 4, 2, 1 },
      { 2, 1, 3, 4 } },
    { { 2, 1, 4, 3 },
      { 3, 4, 1, 2 },
      { 4, 2, 3, 1 },
      { 1, 3, 2, 4 } }
  };

  @Test
  public void testSolvePuzzle1() {
    assertEquals(Skyscrapers.solvePuzzle(clues[0]), outcomes[0]);
  }

  @Test
  public void testSolvePuzzle2() {
    assertEquals(Skyscrapers.solvePuzzle(clues[1]), outcomes[1]);
  }
}
