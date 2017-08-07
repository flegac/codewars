package fr.flegac.codewars.skyscrappers.permutations;

import java.util.BitSet;
import java.util.Iterator;

public class PermutationIterator implements Iterator<Perm>, Iterable<Perm> {
  private final WordIterator wordIterator;
  private Perm next;

  public PermutationIterator(final int size) {
    wordIterator = new WordIterator(size);
    computeNext();
  }

  @Override
  public boolean hasNext() {
    return next != null;
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
    final Perm result = next;
    computeNext();
    return result;
  }

  private void computeNext() {
    while (wordIterator.hasNext()) {
      next = wordIterator.next();
      if (isValidPermutation()) {
        return;
      }
    }
    next = null;
  }

  private boolean isValidPermutation() {
    final int size = next.size();

    final BitSet set = new BitSet(size);
    for (int i = 0; i < size; i++) {
      set.set(next.get(i));
    }
    return set.cardinality() == size;
  }

}