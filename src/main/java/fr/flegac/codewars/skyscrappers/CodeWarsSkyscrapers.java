package fr.flegac.codewars.skyscrappers;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Solver allow to solve skycrapers problem instances really fast (for handled sizes).
 *
 * The algorithm consists in applying a set of resolution rules on the problem until no more improvement is possible.
 * Hopefully, this means the solution is found :)
 *
 * Github code :
 * https://github.com/flegac/codewars/tree/master/src/main/java/fr/flegac/codewars/skyscrappers
 *
 * About:
 * - Fast : Find all solutions in less than one second for sizes up to 8
 * - Simple : Problems are solved using multiple, independent and simple resolution rules
 * - Complexity of O(N!) --> brut force would be O(N^(N*N))
 * - developed using TDD methodology
 *
 * Main ideas:
 * - encode partial solutions with BitSet of still possible values for each cell
 * - a row/column is always a permutation
 * - each clue on a row/column exclude some permutations
 * - clues pairs (couple of clues on a same row/column) exclude even more permutations !
 * - precompute a map of cluePair -> possible permutation
 * - use the map to guess which values are possible in a specific cell
 *
 *
 * Optimizations (DONE) :
 * - fill the cluePair->permutations map only with relevant cluePair (cluePair found in the specific problem instance)
 *
 * Optimizations (TODO):
 * - use Steinhaus–Johnson–Trotter algorithm for iteration over all permutations
 *
 * Problems:
 * - could not work with grids bigger than something like 9-10 because of the potential cost in memory
 * - the permutation iterator class is not optimized at all (which is not a problem for size up to 8)
 *
 */
public class CodeWarsSkyscrapers {
  private static final Solver SOLVER = new Solver();

  public static int[][] solvePuzzle(final int[] clues) {
    final Problem problem = new Problem(clues);
    SOLVER.solve(problem);
    return problem.solution().toArray();
  }

}

class CluePair {

  private static int countLeftRightMaximums(final Perm permutation) {
    int maximums = 0;
    int maxValue = -1;
    for (int i = 0; i < permutation.size(); i++) {
      if (permutation.get(i) > maxValue) {
        maximums++;
        maxValue = permutation.get(i);
      }
    }
    return maximums;
  }

  private final int first;

  private final int last;

  public CluePair(final int first, final int last) {
    this.first = first;
    this.last = last;
  }

  public CluePair(final Perm permutation) {
    first = countLeftRightMaximums(permutation);
    permutation.reverse();
    last = countLeftRightMaximums(permutation);
    permutation.reverse();
  }

  public CluePair end() {
    return new CluePair(0, last);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CluePair other = (CluePair) obj;
    if (last != other.last) {
      return false;
    }
    if (first != other.first) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + last;
    result = prime * result + first;
    return result;
  }

  public CluePair start() {
    return new CluePair(first, 0);
  }

  @Override
  public String toString() {
    return "[" + first + "," + last + "]";
  }

}

class LogicalSolver implements SolverRule {

  private static Map<Integer, BitSet> generateConstraints(final int size, final Set<Perm> permutations) {
    final Map<Integer, BitSet> constraints = new HashMap<>();
    for (int i = 0; i < size; i++) {
      constraints.put(i, new BitSet(size));
    }
    for (final Perm perm : permutations) {
      for (int i = 0; i < size; i++) {
        constraints.get(i).set(perm.get(i));
      }
    }
    return constraints;
  }

  @Override
  public void apply(final Problem problem) {
    final int size = problem.solution().size();
    for (int y = 0; y < size; y++) {
      applyRowConstraints(problem, y);
      problem.transpose();
      applyRowConstraints(problem, y);
      problem.transpose();
    }
  }

  private void applyRowConstraints(final Problem problem, final int y) {
    final Set<Perm> permutations = problem.getRowCompatiblePermutations(y);
    if (permutations.isEmpty()) {
      return;
    }

    final Solution solution = problem.solution();
    final int size = solution.size();
    final Map<Integer, BitSet> constraints = generateConstraints(size, permutations);
    for (int x = 0; x < size; x++) {
      final BitSet availabilities = constraints.get(x);
      solution.keepOnly(solution.index(x, y), availabilities);
    }
  }

}

class Perm {
  private boolean reversed;
  private final int[] permutation;

  public Perm(final int... permutation) {
    this.permutation = permutation;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Perm other = (Perm) obj;
    if (!Arrays.equals(permutation, other.permutation)) {
      return false;
    }
    if (reversed != other.reversed) {
      return false;
    }
    return true;
  }

  public int get(final int i) {
    return reversed ? permutation[size() - 1 - i] : permutation[i];
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(permutation);
    result = prime * result + (reversed ? 1231 : 1237);
    return result;
  }

  public void reverse() {
    this.reversed = !reversed;
  }

  public int size() {
    return permutation.length;
  }

  @Override
  public String toString() {
    return Arrays.toString(permutation);
  }

}

class PermutationIterator implements Iterator<Perm>, Iterable<Perm> {
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

class Problem {
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

class Solution {
  private boolean transposed;
  private final int size;
  private final BitSet data;

  public Solution(final int size) {
    this.size = size;
    data = new BitSet(size * size * size);
    data.set(0, size * size * size);
  }

  public int cellNumber() {
    return size * size;
  }

  public int distanceFromResolution() {
    return data.cardinality() - cellNumber();
  }

  public BitSet getCellAvailabilities(final int cellId) {
    return data.get(size * cellId, size * cellId + size);
  }

  public int getValue(final int cellId) {
    final BitSet values = getCellAvailabilities(cellId);
    if (!isFixed(cellId)) {
      throw new RuntimeException();
    }

    return values.nextSetBit(0);
  }

  public int index(final int x, final int y) {
    return transposed ? (y + x * size) : (x + y * size);
  }

  public boolean isFixed(final int cellId) {
    return getCellAvailabilities(cellId).cardinality() == 1;
  }

  public boolean isPossible(final int cellId, final int value) {
    return getCellAvailabilities(cellId).get(value);
  }

  public void keepOnly(final int cellId, final BitSet values) {
    for (int value = 0; value < size; value++) {
      if (!values.get(value)) {
        data.set(size * cellId + value, false);
      }
    }
  }

  public void keepOnly(final int cellId, final int... values) {
    final BitSet set = new BitSet();
    for (final int value : values) {
      set.set(value);
    }
    keepOnly(cellId, set);
  }

  public void remove(final int cellId, final int... values) {
    for (final int value : values) {
      data.set(size * cellId + value, false);
    }
  }

  public int size() {
    return size;
  }

  public int[][] toArray() {
    final int[][] output = new int[size][size];

    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        final int cellId = index(x, y);
        output[y][x] = getCellAvailabilities(cellId).nextSetBit(0) + 1;
      }
    }

    return output;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        builder.append(cellToString(index(x, y)));
      }
      builder.append('\n');
    }
    return builder.toString();
  }

  public void transpose() {
    transposed = !transposed;
  }

  private String cellToString(final int cellId) {
    final BitSet set = getCellAvailabilities(cellId);
    final StringBuilder builder = new StringBuilder();
    builder.append("[");
    for (int value = 0; value < size; value++) {
      builder.append(set.get(value) ? "" + (value + 1) : ' ');
    }
    builder.append("]");
    return builder.toString();

  }

}

class Solver {
  private final List<SolverRule> solverRules = new LinkedList<>();

  public Solver() {
    solverRules.add(new UniqueOnCellSolver());
    solverRules.add(new UniqueOnLineSolver());
    solverRules.add(new LogicalSolver());
  }

  public void solve(final Problem problem) {
    final Solution solution = problem.solution();
    int oldDistance;
    do {
      oldDistance = solution.distanceFromResolution();
      for (final SolverRule solver : solverRules) {
        solver.apply(problem);
      }
    } while (solution.distanceFromResolution() < oldDistance);
  }
}

interface SolverRule {
  void apply(Problem problem);
}

class UniqueOnCellSolver implements SolverRule {

  @Override
  public void apply(final Problem problem) {
    final Solution solution = problem.solution();

    for (int cellId = 0; cellId < solution.cellNumber(); cellId++) {
      if (solution.isFixed(cellId)) {
        apply(solution, cellId);
      }
    }
  }

  private void apply(final Solution solution, final int cellId) {
    final int size = solution.size();
    final int row = cellId / size;
    final int col = cellId % size;
    final int value = solution.getValue(cellId);

    for (int i = 0; i < size; i++) {
      if (i != row) {
        solution.remove(solution.index(col, i), value);
      }
      if (i != col) {
        solution.remove(solution.index(i, row), value);
      }
    }
  }
}

class UniqueOnLineSolver implements SolverRule {
  private static final int NOT_FOUND = -1;

  @Override
  public void apply(final Problem problem) {
    final int size = problem.size();
    final Solution solution = problem.solution();

    for (int value = 0; value < size; value++) {
      for (int x = 0; x < size; x++) {
        findRowWhereValueIsUniqueOnColumn(solution, value, x);

        solution.transpose();
        findRowWhereValueIsUniqueOnColumn(solution, value, x);
        solution.transpose();
      }
    }
  }

  private void findRowWhereValueIsUniqueOnColumn(final Solution solution, final int value, final int x) {
    int result = NOT_FOUND;
    for (int y = 0; y < solution.size(); y++) {
      if (!solution.isPossible(solution.index(x, y), value)) {
        continue;
      }
      if (result != NOT_FOUND) {
        return;
      }
      result = y;
    }
    solution.keepOnly(solution.index(x, result), value);
  }

}

class WordIterator implements Iterator<Perm>, Iterable<Perm> {
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
