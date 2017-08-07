package fr.flegac.codewars.skyscrappers.solver.rules;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import fr.flegac.codewars.skyscrappers.permutations.Perm;
import fr.flegac.codewars.skyscrappers.permutations.PermutationIterator;
import fr.flegac.codewars.skyscrappers.problem.CluePair;
import fr.flegac.codewars.skyscrappers.problem.Solution;

/**
 * When:
 *
 * Then:
 *
 */
public class CluesSolver implements SolverRule {
  private final int size;

  private CluePair[] rows;

  private CluePair[] cols;

  private final Set<CluePair> relevantClues = new HashSet<>();

  private final Map<CluePair, Set<Perm>> clueToPermutations = new HashMap<>();

  public CluesSolver(final int[] clues) {
    super();
    size = clues.length / 4;
    initializeFromClues(clues);
  }

  @Override
  public void apply(final Solution solution) {

    for (int id1 = 0; id1 < size; id1++) {
      final Map<Integer, BitSet> rowConstraints = generateConstraints(getRowPermutations(solution, id1));
      final Map<Integer, BitSet> colConstraints = generateConstraints(getColPermutations(solution, id1));
      for (int id2 = 0; id2 < size; id2++) {
        final BitSet rowAvailabilities = rowConstraints.get(id2);
        solution.keepOnly(solution.index(id2, id1), rowAvailabilities);
        final BitSet colAvailabilities = colConstraints.get(id2);
        solution.keepOnly(solution.index(id1, id2), colAvailabilities);
      }
    }
  }

  public Map<Integer, BitSet> generateConstraints(final Set<Perm> permutations) {
    final Map<Integer, BitSet> constraints = new HashMap<>();
    for (int i = 0; i < size; i++) {
      constraints.put(i, new BitSet(size));
    }
    if (permutations == null || permutations.size() == 0) {
      for (int i = 0; i < size; i++) {
        constraints.get(i).set(0, size, true);
      }
    } else {
      for (final Perm perm : permutations) {
        for (int i = 0; i < size; i++) {
          constraints.get(i).set(perm.get(i));
        }
      }
    }

    return constraints;
  }

  public Set<Perm> getColPermutations(final Solution solution, final int col) {
    final Set<Perm> permutations = clueToPermutations.get(cols[col]);
    if (permutations == null) {
      return null;
    }
    return permutations.stream()
        .filter(x -> isValidCol(solution, x, col))
        .collect(Collectors.toSet());
  }

  public Set<Perm> getRowPermutations(final Solution solution, final int row) {
    final Set<Perm> permutations = clueToPermutations.get(rows[row]);
    if (permutations == null) {
      return null;
    }
    return permutations.stream()
        .filter(x -> isValidRow(solution, x, row))
        .collect(Collectors.toSet());
  }

  private void generateClueToPermutationsTable() {
    for (final CluePair clue : relevantClues) {
      clueToPermutations.put(clue, new HashSet<>());
      clueToPermutations.put(clue.start(), new HashSet<>());
      clueToPermutations.put(clue.end(), new HashSet<>());
    }

    final PermutationIterator gen = new PermutationIterator(size);

    for (final Perm perm : gen) {
      final CluePair clue = new CluePair(perm);
      final CluePair start = clue.start();
      final CluePair end = clue.end();

      if (clueToPermutations.containsKey(clue)) {
        clueToPermutations.get(clue).add(perm);
      }
      if (clueToPermutations.containsKey(start)) {
        clueToPermutations.get(start).add(perm);
      }
      if (clueToPermutations.containsKey(end)) {
        clueToPermutations.get(end).add(perm);
      }

    }
  }

  private void initializeFromClues(final int[] clues) {
    rows = new CluePair[size];
    cols = new CluePair[size];
    for (int i = 0; i < size; i++) {
      rows[i] = new CluePair(clues[4 * size - 1 - i], clues[i + size]);
      cols[i] = new CluePair(clues[i], clues[3 * size - 1 - i]);

      relevantClues.add(rows[i]);
      relevantClues.add(rows[i].start());
      relevantClues.add(rows[i].end());

      relevantClues.add(cols[i]);
      relevantClues.add(cols[i].start());
      relevantClues.add(cols[i].end());
    }

    generateClueToPermutationsTable();
  }

  private boolean isValidCol(final Solution solution, final Perm perm, final int col) {
    for (int i = 0; i < perm.size(); i++) {
      if (!solution.isPossible(solution.index(col, i), perm.get(i))) {
        return false;
      }
    }
    return true;
  }

  private boolean isValidRow(final Solution solution, final Perm perm, final int row) {
    for (int i = 0; i < perm.size(); i++) {
      if (!solution.isPossible(solution.index(i, row), perm.get(i))) {
        return false;
      }
    }
    return true;
  }

}
