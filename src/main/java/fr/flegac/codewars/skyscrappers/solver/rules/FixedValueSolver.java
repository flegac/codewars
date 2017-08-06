package fr.flegac.codewars.skyscrappers.solver.rules;

import fr.flegac.codewars.skyscrappers.problem.Solution;

@Deprecated
public class FixedValueSolver implements SolverRule {

  @Override
  public void apply(final Solution solution) {
    new UniqueOnCellSolver().apply(solution);
    new UniqueOnRowSolver().apply(solution);
    new UniqueOnColSolver().apply(solution);
  }

}
