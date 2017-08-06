package fr.flegac.codewars.skyscrappers.problem;

import fr.flegac.codewars.skyscrappers.permutations.Perm;

/**
 * CluePair is a couple of clues taken on the same row or column.
 * The first and last clues are respectively the left (or top) one and the right (or bottom) one of a row (or column)
 *
 */
public class CluePair {
  public static final CluePair UNIVERSAL_CLUE = new CluePair(0, 0);

  public final int first;
  public final int last;

  public CluePair(final int first, final int last) {
    this.first = first;
    this.last = last;
  }

  public CluePair(final Perm permutation) {
    final int size = permutation.size();
    int _first = 0, _last = 0;
    int firstMax = -1, lastMax = -1;
    for (int i = 0; i < size; i++) {
      if (permutation.get(i) > firstMax) {
        _first++;
        firstMax = permutation.get(i);
      }
      if (permutation.get(size - 1 - i) > lastMax) {
        _last++;
        lastMax = permutation.get(size - 1 - i);
      }
    }
    first = _first;
    last = _last;
  }

  public CluePair end() {
    return new CluePair(0, last);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CluePair other = (CluePair) obj;
    if (last != other.last) {
      return false;
    }
    if (first != other.first) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + last;
    result = prime * result + first;
    return result;
  }

  public CluePair start() {
    return new CluePair(first, 0);
  }

  @Override
  public String toString() {
    return "[" + first + "," + last + "]";
  }

}