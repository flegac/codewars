package fr.flegac.codewars.skyscrappers.permutations;

import java.util.Iterator;

/**
 * WordIterator(N) iterates over all words in {0,...,N-1}* of length N
 *
 */
public class WordIterator implements Iterator<Perm>, Iterable<Perm> {
  private static final int NO_NEXT_DIGIT = -1;

  private final int[] word;

  private int nextDigit = 0;

  public WordIterator(final int size) {
    this.word = new int[size];
  }

  @Override
  public boolean hasNext() {
    return nextDigit != NO_NEXT_DIGIT;
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
    final Perm result = new Perm(word.clone());
    nextWord();
    return result;
  }

  private void fillWithZeroUpToNextDigit() {
    for (int i = 0; i < nextDigit; i++) {
      word[i] = 0;
    }
  }

  private void nextWord() {
    if (nextDigit == NO_NEXT_DIGIT) {
      throw new RuntimeException();
    }
    word[nextDigit]++;
    fillWithZeroUpToNextDigit();
    updateNextDigit();
  }

  private void updateNextDigit() {
    for (int i = 0; i < word.length; i++) {
      if (word[i] < word.length - 1) {
        nextDigit = i;
        return;
      }
    }
    nextDigit = NO_NEXT_DIGIT;
  }
}