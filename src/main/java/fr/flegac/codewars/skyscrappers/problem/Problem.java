package fr.flegac.codewars.skyscrappers.problem;

public class Problem {
  private final int size;

  private final int[] clues;

  private final Solution solution;

  public Problem(final int[] clues) {
    this.size = clues.length / 4;
    this.clues = clues;
    this.solution = new Solution(size);
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
