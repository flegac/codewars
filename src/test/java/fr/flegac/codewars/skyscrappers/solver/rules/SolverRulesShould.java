package fr.flegac.codewars.skyscrappers.solver.rules;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import fr.flegac.codewars.skyscrappers.problem.Solution;

public class SolverRulesShould {
  private static final int SIZE = 6;

  private static SolverRule uniqueOnColSolver = new UniqueOnColSolver();
  private static SolverRule uniqueOnRowSolver = new UniqueOnRowSolver();
  private static SolverRule uniqueOnCellSolver = new UniqueOnCellSolver();

  @Test
  public void applyUniqueOnCell() throws Exception {
    final Solution solution = new Solution(SIZE);

    final int x = 2;
    final int y = 4;

    final int id = solution.index(x, y);
    final int value = 0;

    solution.keepOnly(id, value);

    for (int col = 0; col < SIZE; col++) {
      final int cellId = solution.index(col, y);
      if (cellId != id) {
        assertThat(solution.getCellAvailabilities(cellId).get(value)).isFalse();
      }
    }

    for (int row = 0; row < SIZE; row++) {
      final int cellId = solution.index(x, row);
      if (cellId != id) {
        assertThat(solution.getCellAvailabilities(cellId).get(value)).isFalse();
      }
    }

  }

  @Test
  public void applyUniqueValueOnCol() throws Exception {
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

    uniqueOnColSolver.apply(solution);

    assertThat(solution.isFixed(cellId)).isTrue();
    assertThat(solution.getValue(cellId)).isEqualTo(value);
  }

  @Test
  public void applyUniqueValueOnRow() throws Exception {
    final Solution solution = new Solution(SIZE);

    final int y = 2;
    final int cellId = solution.index(3, y);
    final int value = 4;

    for (int x = 0; x < SIZE; x++) {
      final int id = solution.index(x, y);
      if (id == cellId) {
        continue;
      }
      solution.remove(id, value);
    }

    uniqueOnRowSolver.apply(solution);

    assertThat(solution.isFixed(cellId)).isTrue();
    assertThat(solution.getValue(cellId)).isEqualTo(value);
  }

}
