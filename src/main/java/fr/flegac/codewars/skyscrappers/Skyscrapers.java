package fr.flegac.codewars.skyscrappers;

import fr.flegac.codewars.skyscrappers.problem.Solution;
import fr.flegac.codewars.skyscrappers.solver.Solver;

public class Skyscrapers {

  public static int[][] solvePuzzle(final int[] clues) {
    final Solver solver = new Solver(clues);
    final Solution solution = solver.solve();

    return solution.toArray();
  }

}
