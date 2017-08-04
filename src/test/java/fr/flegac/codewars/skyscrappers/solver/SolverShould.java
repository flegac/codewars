package fr.flegac.codewars.skyscrappers.solver;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import fr.flegac.codewars.skyscrappers.problem.Solution;

public class SolverShould {
  private static final int SIZE = 6;

  @Test
  public void nullifyDistanceFromResolution() throws Exception {
    final Solver solver = new Solver(new int[] {
      3, 2, 2, 3, 2, 1,
      1, 2, 3, 3, 2, 2,
      5, 1, 2, 2, 4, 3,
      3, 2, 1, 2, 2, 4
    });

    final Solution solution = solver.solve();
    assertThat(solution.distanceFromResolution()).isGreaterThanOrEqualTo(0);
    assertThat(solution.distanceFromResolution()).isLessThanOrEqualTo(SIZE * SIZE * SIZE - SIZE * SIZE);
  }

  @Test
  public void solve() throws Exception {

    final Solver solver = new Solver(new int[] {
      3, 2, 2, 3, 2, 1,
      1, 2, 3, 3, 2, 2,
      5, 1, 2, 2, 4, 3,
      3, 2, 1, 2, 2, 4
    });

    final Solution solution = solver.solve();

    assertThat(solution.distanceFromResolution()).isEqualTo(0);

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        assertThat(solution.isFixed(solution.index(i, j))).isTrue();
      }
    }
  }
}
