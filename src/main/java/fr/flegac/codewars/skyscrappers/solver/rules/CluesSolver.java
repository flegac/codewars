package fr.flegac.codewars.skyscrappers.solver.rules;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import fr.flegac.codewars.skyscrappers.permutations.Perm;
import fr.flegac.codewars.skyscrappers.problem.Problem;
import fr.flegac.codewars.skyscrappers.problem.Solution;

/**
 * When:
 *
 * Then:
 *
 */
public class CluesSolver implements SolverRule {

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

  public Map<Integer, BitSet> generateConstraints(final int size, final Set<Perm> permutations) {
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

  private void applyRowConstraints(final Problem problem, final int y) {
    final Set<Perm> permutations = problem.getRowPermutations(y);

    final Solution solution = problem.solution();
    final int size = solution.size();
    final Map<Integer, BitSet> constraints = generateConstraints(size, permutations);
    for (int x = 0; x < size; x++) {
      final BitSet availabilities = constraints.get(x);
      solution.keepOnly(solution.index(x, y), availabilities);
    }
  }

}
