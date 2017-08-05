package fr.flegac.codewars.skyscrappers.solver.rules;

import java.util.BitSet;
import fr.flegac.codewars.skyscrappers.problem.Solution;

/**
 * When: x is the only column on a row y with a possible value v1
 *
 * Then: no other value than v1 is possible in cell (x,y)
 *
 */
public class UniqueOnColSolver implements SolverRule {

  @Override
  public void apply(final Solution solution) {
    for (int i = 0; i < solution.size(); i++) {
      uniqueOnColSolver(solution, i);
    }
  }

  private void uniqueOnColSolver(final Solution solution, final int col) {
    final int size = solution.size();
    final int[] occurences = new int[size];
    final int[] rows = new int[size];

    for (int row = 0; row < size; row++) {
      final BitSet availability = solution.getCellAvailabilities(solution.index(col, row));
      for (int value = 0; value < size; value++) {
        if (availability.get(value)) {
          occurences[value]++;
          rows[value] = row;
        }
      }
    }

    for (int value = 0; value < size; value++) {
      if (occurences[value] == 1) {
        solution.keepOnly(solution.index(col, rows[value]), value);
      }
    }
  }

}
