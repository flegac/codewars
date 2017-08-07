package fr.flegac.codewars.skyscrappers.permutations;

import java.util.Arrays;

public class Perm {
  private boolean reversed;
  private final int[] permutation;

  public Perm(final int... permutation) {
    this.permutation = permutation;
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
    final Perm other = (Perm) obj;
    if (!Arrays.equals(permutation, other.permutation)) {
      return false;
    }
    if (reversed != other.reversed) {
      return false;
    }
    return true;
  }

  public int get(final int i) {
    return reversed ? permutation[size() - 1 - i] : permutation[i];
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(permutation);
    result = prime * result + (reversed ? 1231 : 1237);
    return result;
  }

  public void reverse() {
    this.reversed = !reversed;
  }

  public int size() {
    return permutation.length;
  }

  @Override
  public String toString() {
    return Arrays.toString(permutation);
  }

}