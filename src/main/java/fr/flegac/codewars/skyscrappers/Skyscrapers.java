package fr.flegac.codewars.skyscrappers;

import fr.flegac.codewars.skyscrappers.problem.Problem;
import fr.flegac.codewars.skyscrappers.solver.Solver;

public class Skyscrapers {
  private static final Solver SOLVER = new Solver();

  public static int[][] solvePuzzle(final int[] clues) {
    final Problem problem = new Problem(clues);
    SOLVER.solve(problem);
    return problem.solution().toArray();
  }

}
