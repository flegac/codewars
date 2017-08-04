package fr.flegac.codewars.skyscrappers.solver.rules;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import fr.flegac.codewars.skyscrappers.problem.CluePair;
import fr.flegac.codewars.skyscrappers.problem.Solution;
import fr.flegac.codewars.skyscrappers.utils.Perm;
import fr.flegac.codewars.skyscrappers.utils.PermGenerator;

/**
 *
 * When:
 *
 *
 * Then:
 *
 *
 */
public class CluesSolver implements SolverRule {

  private static FixedValueSolver fixedSolver = new FixedValueSolver();

  private final int[] clues;

  private final int size;

  private CluePair[] rows;

  private CluePair[] cols;

  private final Set<CluePair> specificClues = new HashSet<>();

  private final Map<CluePair, Set<Perm>> clueToPermutations = new HashMap<>();

  public CluesSolver(final int[] clues) {
    super();
    this.clues = clues;
    size = clues.length / 4;
    initializeClues();
  }

  @Override
  public void apply(final Solution solution) {

    for (int id1 = 0; id1 < size; id1++) {
      final Map<Integer, BitSet> rowConstraints = generateConstraints(getRowPermutations(solution, id1));
      final Map<Integer, BitSet> colConstraints = generateConstraints(getColPermutations(solution, id1));
      for (int id2 = 0; id2 < size; id2++) {
        final BitSet rowAvailabilities = rowConstraints.get(id2);
        solution.keepOnly(solution.index(id2, id1), rowAvailabilities);

        fixedSolver.apply(solution);

        final BitSet colAvailabilities = colConstraints.get(id2);
        solution.keepOnly(solution.index(id1, id2), colAvailabilities);

        fixedSolver.apply(solution);
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
    for (final CluePair clue : specificClues) {
      clueToPermutations.put(clue, new HashSet<>());
      clueToPermutations.put(clue.start(), new HashSet<>());
      clueToPermutations.put(clue.end(), new HashSet<>());
    }

    final PermGenerator gen = new PermGenerator(size);
    while (gen.hasNext()) {
      final Perm perm = gen.next();

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

  private void initializeClues() {
    rows = new CluePair[size];
    cols = new CluePair[size];
    for (int i = 0; i < size; i++) {
      rows[i] = new CluePair(clues[4 * size - 1 - i], clues[i + size]);
      cols[i] = new CluePair(clues[i], clues[3 * size - 1 - i]);
      specificClues.add(rows[i]);
      specificClues.add(cols[i]);
      specificClues.add(cols[i].start());
      specificClues.add(cols[i].end());
      specificClues.add(cols[i].start());
      specificClues.add(cols[i].end());
    }

    generateClueToPermutationsTable();
  }

  private boolean isValidCol(final Solution solution, final Perm perm, final int col) {
    for (int i = 0; i < perm.size(); i++) {
      final BitSet possibilities = solution.getCellAvailabilities(solution.index(col, i));
      if (!possibilities.get(perm.get(i))) {
        return false;
      }
    }
    return true;
  }

  private boolean isValidRow(final Solution solution, final Perm perm, final int row) {
    for (int i = 0; i < perm.size(); i++) {
      final BitSet possibilities = solution.getCellAvailabilities(solution.index(i, row));
      if (!possibilities.get(perm.get(i))) {
        return false;
      }
    }
    return true;
  }

}
