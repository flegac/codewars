package fr.flegac.codewars.skyscrappers.solver;

import fr.flegac.codewars.skyscrappers.problem.Solution;
import fr.flegac.codewars.skyscrappers.solver.rules.CluesSolver;
import fr.flegac.codewars.skyscrappers.solver.rules.FixedValueSolver;

public class Solver {

  private static final int GRID_SIDE_NUMBER = 4;

  private final Solution solution;

  private final FixedValueSolver fixedSolver;
  private final CluesSolver cluesSolver;

  public Solver(final int[] clues) {
    final int size = clues.length / GRID_SIDE_NUMBER;
    solution = new Solution(size);
    fixedSolver = new FixedValueSolver();
    cluesSolver = new CluesSolver(clues);
  }

  public Solution solve() {
    smartSolve();
    return solution;
  }

  private void smartSolve() {
    int oldDistance;
    do {
      oldDistance = solution.distanceFromResolution();
      fixedSolver.apply(solution);
      cluesSolver.apply(solution);
    } while (solution.distanceFromResolution() < oldDistance);
    System.out.println("distance = " + solution.distanceFromResolution());
    System.out.println(solution);
  }
}
