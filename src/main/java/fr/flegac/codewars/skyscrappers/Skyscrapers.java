package fr.flegac.codewars.skyscrappers;

import fr.flegac.codewars.skyscrappers.problem.Problem;
import fr.flegac.codewars.skyscrappers.solver.Solver;

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
public class Skyscrapers {
  private static final Solver SOLVER = new Solver();

  public static int[][] solvePuzzle(final int[] clues) {
    final Problem problem = new Problem(clues);
    SOLVER.solve(problem);
    return problem.solution().toArray();
  }

}
