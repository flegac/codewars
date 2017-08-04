package fr.flegac.codewars.skyscrappers;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class SolutionTest {

  @Test
  public void solvePuzzle1() {
    final int[] clues = new int[] {
      3, 2, 2, 3, 2, 1,
      1, 2, 3, 3, 2, 2,
      5, 1, 2, 2, 4, 3,
      3, 2, 1, 2, 2, 4
    };

    final int[][] expected = new int[][] {
      new int[] {
        2, 1, 4, 3, 5, 6
      },
      new int[] {
        1, 6, 3, 2, 4, 5
      },
      new int[] {
        4, 3, 6, 5, 1, 2
      },
      new int[] {
        6, 5, 2, 1, 3, 4
      },
      new int[] {
        5, 4, 1, 6, 2, 3
      },
      new int[] {
        3, 2, 5, 4, 6, 1
      }
    };

    final int[][] actual = Skyscrapers.solvePuzzle(clues);
    assertArrayEquals(expected, actual);
  }

  @Test
  public void solvePuzzle2() {
    final int[] clues = new int[] {
      0, 0, 0, 2, 2, 0,
      0, 0, 0, 6, 3, 0,
      0, 4, 0, 0, 0, 0,
      4, 4, 0, 3, 0, 0
    };

    final int[][] expected = new int[][] {
      new int[] {
        5, 6, 1, 4, 3, 2
      },
      new int[] {
        4, 1, 3, 2, 6, 5
      },
      new int[] {
        2, 3, 6, 1, 5, 4
      },
      new int[] {
        6, 5, 4, 3, 2, 1
      },
      new int[] {
        1, 2, 5, 6, 4, 3
      },
      new int[] {
        3, 4, 2, 5, 1, 6
      }
    };

    final int[][] actual = Skyscrapers.solvePuzzle(clues);
    assertArrayEquals(expected, actual);
  }

  @Test
  public void solvePuzzle3() {
    final int[] clues = new int[] {
      0, 3, 0, 5, 3, 4,
      0, 0, 0, 0, 0, 1,
      0, 3, 0, 3, 2, 3,
      3, 2, 0, 3, 1, 0
    };

    final int[][] expected = new int[][] {
      new int[] {
        5, 2, 6, 1, 4, 3
      },
      new int[] {
        6, 4, 3, 2, 5, 1
      },
      new int[] {
        3, 1, 5, 4, 6, 2
      },
      new int[] {
        2, 6, 1, 5, 3, 4
      },
      new int[] {
        4, 3, 2, 6, 1, 5
      },
      new int[] {
        1, 5, 4, 3, 2, 6
      }
    };

    final int[][] actual = Skyscrapers.solvePuzzle(clues);
    assertArrayEquals(expected, actual);
  }
}