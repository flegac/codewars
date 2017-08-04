package fr.flegac.codewars.skyscrappers.utils;

import java.util.BitSet;

public class PermGenerator {
    private static final int NO_NEXT_DIGIT = -1;

    private int[] combination;

    private int nextDigit = 0;

    private boolean hasNext;

    public PermGenerator(int size) {
        combination = new int[size];
        computeNext();
    }

    public boolean hasNext() {
        return hasNext;
    }

    public Perm next() {
        if (!hasNext()) {
            throw new RuntimeException();
        }
        Perm result = new Perm(combination);
        computeNext();
        return result;
    }

    private void computeNext() {
        while (nextDigit != NO_NEXT_DIGIT) {
            nextCombination();
            if (isValidPermutation()) {
                hasNext = true;
                return;
            }
        }
        hasNext = false;
    }

    private boolean isValidPermutation() {
        BitSet set = new BitSet(combination.length);
        for (int i = 0; i < combination.length; i++) {
            set.set(combination[i]);
        }
        return set.cardinality() == combination.length;
    }

    private void nextCombination() {
        if (nextDigit == NO_NEXT_DIGIT) {
            throw new RuntimeException();
        }
        combination[nextDigit]++;
        fillWithZeroUpToNextDigit();
        updateNextDigit();
    }

    private void updateNextDigit() {
        for (int i = 0; i < combination.length; i++) {
            if (combination[i] < combination.length - 1) {
                nextDigit = i;
                return;
            }
        }
        nextDigit = NO_NEXT_DIGIT;
    }

    private void fillWithZeroUpToNextDigit() {
        for (int i = 0; i < nextDigit; i++) {
            combination[i] = 0;
        }
    }
}