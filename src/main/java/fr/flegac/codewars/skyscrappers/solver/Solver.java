package fr.flegac.codewars.skyscrappers.solver;

import java.util.LinkedList;
import java.util.List;
import fr.flegac.codewars.skyscrappers.problem.Problem;
import fr.flegac.codewars.skyscrappers.problem.Solution;
import fr.flegac.codewars.skyscrappers.solver.rules.LogicalSolver;
import fr.flegac.codewars.skyscrappers.solver.rules.SolverRule;
import fr.flegac.codewars.skyscrappers.solver.rules.UniqueOnCellSolver;
import fr.flegac.codewars.skyscrappers.solver.rules.UniqueOnLineSolver;

/**
 * Solver allow to solve skycrapers problem instances.
 *
 * The algorithm consists in applying a set of resolution rules on the problem until no more improvement is possible.
 * Hopefully, this means the solution is found :)
 *
 */
public class Solver {
  private final List<SolverRule> solverRules = new LinkedList<>();

  public Solver() {
    solverRules.add(new UniqueOnCellSolver());
    solverRules.add(new UniqueOnLineSolver());
    solverRules.add(new LogicalSolver());
  }

  public void solve(final Problem problem) {
    final Solution solution = problem.solution();
    int oldDistance;
    do {
      oldDistance = solution.distanceFromResolution();
      for (final SolverRule solver : solverRules) {
        solver.apply(problem);
      }
    } while (solution.distanceFromResolution() < oldDistance);
  }
}
