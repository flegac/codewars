package fr.flegac.codewars.skyscrappers.solver;

import java.util.LinkedList;
import java.util.List;
import fr.flegac.codewars.skyscrappers.problem.Solution;
import fr.flegac.codewars.skyscrappers.solver.rules.CluesSolver;
import fr.flegac.codewars.skyscrappers.solver.rules.SolverRule;
import fr.flegac.codewars.skyscrappers.solver.rules.UniqueOnCellSolver;
import fr.flegac.codewars.skyscrappers.solver.rules.UniqueOnColSolver;
import fr.flegac.codewars.skyscrappers.solver.rules.UniqueOnRowSolver;

public class Solver {

  private static final int GRID_SIDE_NUMBER = 4;

  private final Solution solution;

  private final List<SolverRule> solvers = new LinkedList<>();

  public Solver(final int[] clues) {
    final int size = clues.length / GRID_SIDE_NUMBER;
    solution = new Solution(size);

    solvers.add(new UniqueOnCellSolver());
    solvers.add(new UniqueOnColSolver());
    solvers.add(new UniqueOnRowSolver());
    solvers.add(new CluesSolver(clues));
  }

  public Solution solve() {
    smartSolve();
    return solution;
  }

  private void smartSolve() {
    int oldDistance;
    do {
      oldDistance = solution.distanceFromResolution();
      for (final SolverRule solver : solvers) {
        solver.apply(solution);
      }
    } while (solution.distanceFromResolution() < oldDistance);
    System.out.println("distance = " + solution.distanceFromResolution());
    System.out.println(solution);
  }
}
