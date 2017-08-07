package fr.flegac.codewars.skyscrappers.solver;

import java.util.LinkedList;
import java.util.List;
import fr.flegac.codewars.skyscrappers.problem.Problem;
import fr.flegac.codewars.skyscrappers.problem.Solution;
import fr.flegac.codewars.skyscrappers.solver.rules.CluesSolver;
import fr.flegac.codewars.skyscrappers.solver.rules.SolverRule;
import fr.flegac.codewars.skyscrappers.solver.rules.UniqueOnCellSolver;
import fr.flegac.codewars.skyscrappers.solver.rules.UniqueOnLineSolver;

public class Solver {

  private final Problem problem;

  private final List<SolverRule> solvers = new LinkedList<>();

  public Solver(final int[] clues) {
    problem = new Problem(clues);

    solvers.add(new UniqueOnCellSolver());
    solvers.add(new UniqueOnLineSolver());
    solvers.add(new CluesSolver());
  }

  public Solution solve() {
    smartSolve();
    return problem.solution();
  }

  private void smartSolve() {
    final Solution solution = problem.solution();
    int oldDistance;
    do {
      oldDistance = solution.distanceFromResolution();
      for (final SolverRule solver : solvers) {
        solver.apply(problem);
      }
    } while (solution.distanceFromResolution() < oldDistance);
    System.out.println("distance = " + solution.distanceFromResolution());
    System.out.println(solution);
  }
}
