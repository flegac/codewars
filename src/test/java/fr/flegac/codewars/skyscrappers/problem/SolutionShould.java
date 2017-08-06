package fr.flegac.codewars.skyscrappers.problem;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.BitSet;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionShould {

  private static final int SIZE = 6;

  @Test
  public void checkIfCellHasFixedValue() throws Exception {
    // given
    final Solution solution = new Solution(SIZE);
    final int id = solution.index(1, 2);
    final int value = 3;

    // when
    solution.keepOnly(id, value);

    // then
    assertThat(solution.isFixed(id)).isTrue();
  }

  @Test
  public void checkIfCellValueIsFixed() throws Exception {
    // given
    final Solution solution = new Solution(SIZE);
    final int cellId = solution.index(2, 2);
    final int value = 3;

    Assertions.assertThatThrownBy(() -> {
      solution.getValue(cellId);
    }).isInstanceOf(new RuntimeException().getClass());

    // when
    solution.keepOnly(cellId, value);

    // then
    assertThat(solution.isFixed(cellId)).isEqualTo(true);
    assertThat(solution.getValue(cellId)).isEqualTo(value);
  }

  @Test
  public void computeCellNumber() throws Exception {
    final Solution solution = new Solution(SIZE);

    assertThat(solution.cellNumber()).isEqualTo(SIZE * SIZE);
  }

  @Test
  public void convertToSolutionArray() throws Exception {
    final Solution solution = new Solution(SIZE);

    for (int cellId = 0; cellId < SIZE * SIZE; cellId++) {
      solution.keepOnly(cellId, cellId % SIZE);
    }

    final int[][] output = solution.toArray();

    for (int x = 0; x < SIZE; x++) {
      for (int y = 0; y < SIZE; y++) {
        assertThat(output[y][x]).isEqualTo(solution.index(x, y) % SIZE + 1);
      }
    }
  }

  @Test
  public void giveAccessToAvailablePossibilitiesOfACell() throws Exception {
    final Solution solution = new Solution(SIZE);

    final int cellID = solution.index(3, 4);
    final BitSet possibilities = solution.getCellAvailabilities(cellID);

    assertThat(possibilities).isNotNull();
    assertThat(possibilities.cardinality()).isEqualTo(SIZE);
  }

  @Test
  public void haveSize() throws Exception {
    final Solution solution = new Solution(SIZE);

    assertThat(solution.size()).isEqualTo(SIZE);
  }

  @Test
  public void keepOnlySomeValuesForACell() throws Exception {
    final Solution solution = new Solution(SIZE);

    final int cellId = solution.index(3, 4);

    final BitSet availability = (BitSet) solution.getCellAvailabilities(cellId).clone();

    solution.keepOnly(cellId, 1, 2, 3, 4);

    final BitSet availability2 = solution.getCellAvailabilities(cellId);

    assertThat(availability.cardinality()).isEqualTo(6);
    assertThat(availability2.cardinality()).isEqualTo(4);
  }

  @Test
  public void provideUniqueIdForEachCell() throws Exception {
    final Solution solution = new Solution(6);

    final int id1 = solution.index(3, 4);
    final int id2 = solution.index(1, 2);

    assertThat(id1).isNotEqualTo(id2);
  }

  @Test
  public void removePossibleValueForACell() throws Exception {
    final Solution solution = new Solution(SIZE);

    final int cellId = solution.index(3, 4);

    final BitSet possibilities = (BitSet) solution.getCellAvailabilities(cellId).clone();

    solution.remove(cellId, 0);

    final BitSet possibilities2 = solution.getCellAvailabilities(cellId);

    assertThat(possibilities).isNotEqualTo(possibilities2);
  }

  @Test
  public void sayIfSomeValueIsPossibleForSomeCell() throws Exception {

    final Solution solution = new Solution(SIZE);

    final int cellId = solution.index(3, 5);
    final int possibleValue = 4;
    final int impossibleValue = 3;

    solution.remove(cellId, impossibleValue);

    assertThat(solution.isPossible(cellId, possibleValue)).isTrue();
    assertThat(solution.isPossible(cellId, impossibleValue)).isFalse();

  }

}
