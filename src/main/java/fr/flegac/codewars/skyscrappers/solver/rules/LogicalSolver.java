package fr.flegac.codewars.skyscrappers.solver.rules;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import fr.flegac.codewars.skyscrappers.permutations.Perm;
import fr.flegac.codewars.skyscrappers.problem.Problem;
import fr.flegac.codewars.skyscrappers.problem.Solution;

/**
 * When: a row (resp. column) is only compatible with a set P of permutations
 *
 * Then: for each position x in the row (resp. column)
 * exclude any value v if no permutation p in P verify p[x] = v
 */
public class LogicalSolver implements SolverRule {

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
