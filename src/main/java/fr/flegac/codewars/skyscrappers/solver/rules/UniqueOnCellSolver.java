package fr.flegac.codewars.skyscrappers.solver.rules;

import fr.flegac.codewars.skyscrappers.problem.Solution;

/**
 * When : v1 is the only possible value in cell with coordinates (x,y)
 *
 * Then : v1 can not be in position (x,i) with i != y or (i,y) with i != x
 */
public class UniqueOnCellSolver implements SolverRule {

  @Override
  public void apply(final Solution solution) {
    for (int id = 0; id < solution.cellNumber(); id++) {
      uniqueOnCell(solution, id);
    }
  }

  private void uniqueOnCell(final Solution solution, final int cellId) {
    if (!solution.isFixed(cellId)) {
      return;
    }
    final int size = solution.size();

    final int row = cellId / size;
    final int col = cellId % size;

    final int value = solution.getValue(cellId);
    for (int i = 0; i < size; i++) {
      if (i != row) {
        solution.remove(solution.index(col, i), value);
      }
      if (i != col) {
        solution.remove(solution.index(i, row), value);
      }
    }
  }
}
