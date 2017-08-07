package fr.flegac.codewars.skyscrappers.problem;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import fr.flegac.codewars.skyscrappers.permutations.Perm;

public class ProblemShould {
  private static final int SIZE = 6;

  private static final int[] CLUES = {
    3, 0, 4, 3, 0, 0,
    0, 2, 0, 3, 4, 1,
    1, 0, 2, 5, 0, 0,
    0, 4, 1, 0, 0, 6
  };

  @Test
  public void giveAccessToDistanceFromResolution() throws Exception {
    final Problem problem = new Problem(CLUES);

    assertThat(problem.distanceFromResolution()).isEqualTo(SIZE * SIZE * SIZE - SIZE * SIZE);
  }

  @Test
  public void giveAccessToGridSize() throws Exception {
    final Problem problem = new Problem(CLUES);
    assertThat(problem.size()).isEqualTo(SIZE);
  }

  @Test
  public void giveAccessToRelevantClues() throws Exception {

    final Problem problem = new Problem(CLUES);

    final Set<CluePair> releventClues = problem.relevantClues();

    assertThat(releventClues).contains(new CluePair(3, 2));
    assertThat(releventClues).contains(new CluePair(3, 0));
    assertThat(releventClues).contains(new CluePair(0, 2));
    assertThat(releventClues).contains(new CluePair(0, 0));
    assertThat(releventClues).contains(new CluePair(6, 0));
  }

  @Test
  public void giveAccessToRowClues() throws Exception {
    final Problem problem = new Problem(CLUES);

    final int row = 3;
    final CluePair expected = new CluePair(1, 3);

    assertThat(problem.rowClues(row)).isEqualTo(expected);
  }

  @Test
  public void giveAccessToRowPermutations() throws Exception {

    final Problem problem = new Problem(CLUES);

    final Set<Perm> actual = problem.getRowCompatiblePermutations(0);

    final Set<Perm> expected = new HashSet<>();
    expected.add(new Perm(0, 1, 2, 3, 4, 5));

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void giveAccessToThePartialSolution() throws Exception {
    final Problem problem = new Problem(CLUES);
    assertThat(problem.solution()).isNotNull();
  }

  @Test
  public void transposeMatrix() throws Exception {
    final Problem problem = new Problem(CLUES);
    final Solution solution = problem.solution();

    final int x = 3;
    final int y = 1;
    final int cellId = solution.index(x, y);

    solution.remove(cellId, 0);

    final BitSet original = (BitSet) solution.getCellAvailabilities(cellId).clone();

    problem.transpose();

    final BitSet transposed = solution.getCellAvailabilities(solution.index(x, y));

    assertThat(original).isNotEqualTo(transposed);
    assertThat(original).isEqualTo(solution.getCellAvailabilities(solution.index(y, x)));

  }

}
