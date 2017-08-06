package fr.flegac.codewars.skyscrappers.solver.rules;

import fr.flegac.codewars.skyscrappers.problem.Solution;

/**
 * When: y is the only row on a column x with a possible value v1
 *
 * Then: no other value than v1 is possible in cell (x,y)
 *
 */
public class UniqueOnRowSolver implements SolverRule {

  @Override
  public void apply(final Solution solution) {
    for (int i = 0; i < solution.size(); i++) {
      uniqueOnRowSolver(solution, i);
    }
  }

  private void uniqueOnRowSolver(final Solution solution, final int row) {
    final int size = solution.size();
    final int[] occurences = new int[size];
    final int[] cols = new int[size];

    for (int col = 0; col < size; col++) {
      final int cellId = solution.index(col, row);
      for (int value = 0; value < size; value++) {
        if (solution.isPossible(cellId, value)) {
          occurences[value]++;
          cols[value] = col;
        }
      }
    }

    for (int value = 0; value < size; value++) {
      if (occurences[value] == 1) {
        solution.keepOnly(solution.index(cols[value], row), value);
      }
    }
  }

}
