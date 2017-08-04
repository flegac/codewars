package fr.flegac.codewars.skyscrappers.utils;

import java.util.Arrays;

public class Perm {
    private int[] permutation;

    public Perm(int[] permutation) {
        this.permutation = permutation.clone();
    }

    public int get(int i) {
        return permutation[i];
    }

    public int size() {
        return permutation.length;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(permutation);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Perm other = (Perm) obj;
        if (!Arrays.equals(permutation, other.permutation)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Arrays.toString(permutation);
    }

}