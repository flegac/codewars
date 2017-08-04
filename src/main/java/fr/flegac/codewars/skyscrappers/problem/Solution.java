package fr.flegac.codewars.skyscrappers.problem;

import java.util.BitSet;

public class Solution {
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

  public int index(final int i, final int j) {
    return i + j * size;
  }

  public boolean isFixed(final int id) {
    return getCellAvailabilities(id).cardinality() == 1;
  }

  public void keepOnly(final int id, final BitSet values) {
    for (int value = 0; value < size; value++) {
      if (!values.get(value)) {
        data.set(size * id + value, false);
      }
    }
  }

  public void keepOnly(final int id, final int... values) {
    final BitSet set = new BitSet();
    for (final int value : values) {
      set.set(value);
    }
    keepOnly(id, set);
  }

  public void remove(final int id, final int... values) {
    for (final int value : values) {
      data.set(size * id + value, false);
    }
  }

  public int size() {
    return size;
  }

  public int[][] toArray() {
    final int[][] output = new int[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        final int id = index(i, j);
        output[j][i] = getCellAvailabilities(id).nextSetBit(0) + 1;
      }
    }

    return output;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        final int id = index(x, y);
        builder.append(cellToString(id));
      }
      builder.append('\n');
    }
    return builder.toString();
  }

  private String cellToString(final int id) {
    final BitSet set = getCellAvailabilities(id);
    final StringBuilder builder = new StringBuilder();
    builder.append("[");
    for (int value = 0; value < size; value++) {
      builder.append(set.get(value) ? "" + (value + 1) : ' ');
    }
    builder.append("]");
    return builder.toString();

  }

}
