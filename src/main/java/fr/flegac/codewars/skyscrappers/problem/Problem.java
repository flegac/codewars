package fr.flegac.codewars.skyscrappers.problem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import fr.flegac.codewars.skyscrappers.permutations.Perm;
import fr.flegac.codewars.skyscrappers.permutations.PermutationIterator;

/**
 * Problem represents an instance of the Skyscrapers problem
 *
 * It computes useful statistics from initial clues and maintains the current solution state.
 *
 */
public class Problem {
  private static final int GRID_SIDE_NUMBER = 4;

  public static Problem noClues(final int size) {
    return new Problem(new int[GRID_SIDE_NUMBER * size]);
  }

  private final int size;
  private CluePair[] rows;
  private CluePair[] cols;
  private final Set<CluePair> relevantClues = new HashSet<>();
  private final Map<CluePair, Set<Perm>> compatiblePermutations = new HashMap<>();

  private final Solution solution;

  public Problem(final int[] clues) {
    this.size = clues.length / GRID_SIDE_NUMBER;
    this.solution = new Solution(size);
    computeClues(clues);
    computeReleventClues();
    computeClueToPermutationsTable();
  }

  public int distanceFromResolution() {
    return solution.distanceFromResolution();
  }

  public Set<Perm> getRowCompatiblePermutations(final int row) {
    final Set<Perm> permutations = compatiblePermutations.get(rows[row]);
    return permutations.stream()
        .filter(x -> isValidRow(x, row))
        .collect(Collectors.toSet());
  }

  public Set<CluePair> relevantClues() {
    return relevantClues;
  }

  public CluePair rowClues(final int y) {
    return rows[y];
  }

  public int size() {
    return size;
  }

  public Solution solution() {
    return solution;
  }

  public void transpose() {
    final CluePair[] tmp = rows;
    rows = cols;
    cols = tmp;
    solution.transpose();
  }

  private void computeClues(final int[] clues) {
    rows = new CluePair[size];
    cols = new CluePair[size];
    for (int i = 0; i < size; i++) {
      rows[i] = new CluePair(clues[4 * size - 1 - i], clues[i + size]);
      cols[i] = new CluePair(clues[i], clues[3 * size - 1 - i]);
    }
  }

  private void computeClueToPermutationsTable() {
    for (final CluePair clue : relevantClues) {
      compatiblePermutations.put(clue, new HashSet<>());
      compatiblePermutations.put(clue.start(), new HashSet<>());
      compatiblePermutations.put(clue.end(), new HashSet<>());
    }

    final PermutationIterator gen = new PermutationIterator(size);

    for (final Perm perm : gen) {
      final CluePair clue = new CluePair(perm);
      final CluePair start = clue.start();
      final CluePair end = clue.end();

      if (compatiblePermutations.containsKey(clue)) {
        compatiblePermutations.get(clue).add(perm);
      }
      if (compatiblePermutations.containsKey(start)) {
        compatiblePermutations.get(start).add(perm);
      }
      if (compatiblePermutations.containsKey(end)) {
        compatiblePermutations.get(end).add(perm);
      }

    }
  }

  private void computeReleventClues() {
    for (final CluePair pair : rows) {
      relevantClues.add(pair);
      relevantClues.add(pair.start());
      relevantClues.add(pair.end());
    }
    for (final CluePair pair : cols) {
      relevantClues.add(pair);
      relevantClues.add(pair.start());
      relevantClues.add(pair.end());
    }
  }

  private boolean isValidRow(final Perm perm, final int row) {
    for (int i = 0; i < perm.size(); i++) {
      if (!solution.isPossible(solution.index(i, row), perm.get(i))) {
        return false;
      }
    }
    return true;
  }
}
