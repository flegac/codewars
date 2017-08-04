package fr.flegac.codewars.skyscrappers.problem;

import fr.flegac.codewars.skyscrappers.utils.Perm;

public class CluePair {

  public static final CluePair UNIVERSAL_CLUE = new CluePair(0, 0);

  public final int start;

  public final int end;

  public CluePair(final int start, final int end) {
    this.start = start;
    this.end = end;
  }

  public CluePair(final Perm permutation) {
    final int size = permutation.size();
    int _start = 0, _end = 0;
    int leftMax = -1, rightMax = -1;
    for (int i = 0; i < size; i++) {
      if (permutation.get(i) > leftMax) {
        _start++;
        leftMax = permutation.get(i);
      }
      if (permutation.get(size - 1 - i) > rightMax) {
        _end++;
        rightMax = permutation.get(size - 1 - i);
      }
    }
    start = _start;
    end = _end;
  }

  public CluePair end() {
    return new CluePair(0, end);
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
    if (end != other.end) {
      return false;
    }
    if (start != other.start) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + end;
    result = prime * result + start;
    return result;
  }

  public CluePair start() {
    return new CluePair(start, 0);
  }

  @Override
  public String toString() {
    return "[" + start + "," + end + "]";
  }

}