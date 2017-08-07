package fr.flegac.codewars.skyscrappers.problem;

import java.util.BitSet;

/**
 * Solution encode a partial solution.
 * A partial solution consist of the set of remainings possible values for each cell of the solution.
 *
 */
public class Solution {
  private boolean transposed;
  private final int size;
  private final BitSet data;

  public Solution(final int size) {
    this.size = size;
    data = new BitSet(size * size * size);
    data.set(0, size * size * size);
  }

  public int cellNumber() {
    return size * size;
  }

  public int distanceFromResolution() {
    return data.cardinality() - cellNumber();
  }

  public BitSet getCellAvailabilities(final int cellId) {
    return data.get(size * cellId, size * cellId + size);
  }

  public int getValue(final int cellId) {
    final BitSet values = getCellAvailabilities(cellId);
    if (!isFixed(cellId)) {
      throw new RuntimeException();
    }

    return values.nextSetBit(0);
  }

  public int index(final int x, final int y) {
    return transposed ? (y + x * size) : (x + y * size);
  }

  public boolean isFixed(final int cellId) {
    return getCellAvailabilities(cellId).cardinality() == 1;
  }

  public boolean isPossible(final int cellId, final int value) {
    return getCellAvailabilities(cellId).get(value);
  }

  public void keepOnly(final int cellId, final BitSet values) {
    for (int value = 0; value < size; value++) {
      if (!values.get(value)) {
        data.set(size * cellId + value, false);
      }
    }
  }

  public void keepOnly(final int cellId, final int... values) {
    final BitSet set = new BitSet();
    for (final int value : values) {
      set.set(value);
    }
    keepOnly(cellId, set);
  }

  public void remove(final int cellId, final int... values) {
    for (final int value : values) {
      data.set(size * cellId + value, false);
    }
  }

  public int size() {
    return size;
  }

  public int[][] toArray() {
    final int[][] output = new int[size][size];

    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        final int cellId = index(x, y);
        output[y][x] = getCellAvailabilities(cellId).nextSetBit(0) + 1;
      }
    }

    return output;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        builder.append(cellToString(index(x, y)));
      }
      builder.append('\n');
    }
    return builder.toString();
  }

  public void transpose() {
    transposed = !transposed;
  }

  private String cellToString(final int cellId) {
    final BitSet set = getCellAvailabilities(cellId);
    final StringBuilder builder = new StringBuilder();
    builder.append("[");
    for (int value = 0; value < size; value++) {
      builder.append(set.get(value) ? "" + (value + 1) : ' ');
    }
    builder.append("]");
    return builder.toString();

  }

}
