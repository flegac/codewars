package fr.flegac.codewars.skyscrappers.solver.rules;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import fr.flegac.codewars.skyscrappers.problem.Solution;

public class SolverRulesShould {
  private static final int SIZE = 6;

  private static SolverRule uniqueOnLineSolver = new UniqueOnLineSolver();
  private static SolverRule uniqueOnCellSolver = new UniqueOnCellSolver();

  @Test
  public void applyUniqueOnCell() throws Exception {
    final Solution solution = new Solution(SIZE);

    final int x = 2;
    final int y = 4;

    final int id = solution.index(x, y);
    final int value = 0;

    solution.keepOnly(id, value);

    uniqueOnCellSolver.apply(solution);

    for (int col = 0; col < SIZE; col++) {
      final int cellId = solution.index(col, y);
      if (cellId != id) {
        assertThat(solution.isPossible(cellId, value)).isFalse();
      }
    }

    for (int row = 0; row < SIZE; row++) {
      final int cellId = solution.index(x, row);
      if (cellId != id) {
        assertThat(solution.isPossible(cellId, value)).isFalse();
      }
    }

  }

  @Test
  public void applyUniqueValueOnLine() throws Exception {
    final Solution solution = new Solution(SIZE);

    final int x = 2;
    final int cellId = solution.index(x, 1);
    final int value = 2;

    for (int y = 0; y < SIZE; y++) {
      final int id = solution.index(x, y);
      if (id == cellId) {
        continue;
      }
      solution.remove(id, value);
    }

    uniqueOnLineSolver.apply(solution);

    assertThat(solution.isFixed(cellId)).isTrue();
    assertThat(solution.getValue(cellId)).isEqualTo(value);
  }

}
