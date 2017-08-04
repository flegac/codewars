package fr.flegac.codewars.skyscrappers.problem;

public class Problem {
    private int size;

    private int[] clues;

    private Solution solution;

    public Problem(int[] clues) {
        size = clues.length / 4;
        this.clues = clues;

        solution = new Solution(size);
    }

    public int[] initialClues() {
        return clues;
    }

    public int size() {
        return size;
    }

    public Solution solution() {
        return solution;
    }

}
