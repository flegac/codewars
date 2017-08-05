package fr.flegac.codewars.skyscrappers.problem;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class ProblemShould {
  private static final int SIZE = 6;

  private static final int[] CLUES = {
    0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0
  };

  @Test
  public void giveAccessToGridSize() throws Exception {
    final Problem problem = new Problem(CLUES);
    assertThat(problem.size()).isEqualTo(SIZE);
  }

  @Test
  public void giveAccessToInitialClues() throws Exception {

    final Problem problem = new Problem(CLUES);
    assertThat(problem.initialClues()).isNotNull();
  }

  @Test
  public void giveAccessToThePartialSolution() throws Exception {
    final Problem problem = new Problem(CLUES);
    assertThat(problem.solution()).isNotNull();
  }

}
