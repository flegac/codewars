package fr.flegac.codewars.skyscrappers.solver.rules;

import fr.flegac.codewars.skyscrappers.problem.Solution;

/**
 * When: x is the only column on a row y with a possible value v1
 *
 * Then: no other value than v1 is possible in cell (x,y)
 *
 */
public class UniqueOnLineSolver implements SolverRule {
  private static final int NOT_FOUND = -1;

  @Override
  public void apply(final Solution solution) {
    final int size = solution.size();

    for (int value = 0; value < size; value++) {
      for (int x = 0; x < size; x++) {
        findRowWhereAGivenValueIsUnique(solution, value, x);

        // apply on transposed matrix
        solution.transpose();
        findRowWhereAGivenValueIsUnique(solution, value, x);
        solution.transpose();
      }
    }
  }

  private void findRowWhereAGivenValueIsUnique(final Solution solution, final int value, final int x) {
    int result = NOT_FOUND;
    for (int y = 0; y < solution.size(); y++) {
      if (!solution.isPossible(solution.index(x, y), value)) {
        continue;
      }
      if (result != NOT_FOUND) {
        return;
      }
      result = y;
    }
    solution.keepOnly(solution.index(x, result), value);
  }

}
