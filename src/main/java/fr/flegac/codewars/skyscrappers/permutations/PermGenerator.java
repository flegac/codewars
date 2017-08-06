package fr.flegac.codewars.skyscrappers.permutations;

import java.util.BitSet;
import java.util.Iterator;

/**
 * Iterator over all possible permutations
 *
 * Work by iterating all possible words on {0, ..., n-1} alphabet and selecting only permutations.
 */
public class PermGenerator implements Iterator<Perm>, Iterable<Perm> {
  private static final int NO_NEXT_DIGIT = -1;

  private final int[] permutation;

  private int nextDigit = 0;

  private boolean hasNext;

  public PermGenerator(final int size) {
    permutation = new int[size];
    computeNext();
  }

  @Override
  public boolean hasNext() {
    return hasNext;
  }

  @Override
  public Iterator<Perm> iterator() {
    return this;
  }

  @Override
  public Perm next() {
    if (!hasNext()) {
      throw new RuntimeException();
    }
    final Perm result = new Perm(permutation);
    computeNext();
    return result;
  }

  private void computeNext() {
    while (nextDigit != NO_NEXT_DIGIT) {
      nextWord();
      if (isValidPermutation()) {
        hasNext = true;
        return;
      }
    }
    hasNext = false;
  }

  private void fillWithZeroUpToNextDigit() {
    for (int i = 0; i < nextDigit; i++) {
      permutation[i] = 0;
    }
  }

  private boolean isValidPermutation() {
    final BitSet set = new BitSet(permutation.length);
    for (int i = 0; i < permutation.length; i++) {
      set.set(permutation[i]);
    }
    return set.cardinality() == permutation.length;
  }

  private void nextWord() {
    if (nextDigit == NO_NEXT_DIGIT) {
      throw new RuntimeException();
    }
    permutation[nextDigit]++;
    fillWithZeroUpToNextDigit();
    updateNextDigit();
  }

  private void updateNextDigit() {
    for (int i = 0; i < permutation.length; i++) {
      if (permutation[i] < permutation.length - 1) {
        nextDigit = i;
        return;
      }
    }
    nextDigit = NO_NEXT_DIGIT;
  }
}