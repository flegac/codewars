package fr.flegac.codewars.skyscrappers.permutations;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class PermIteratorShould {

  // WARNING : my first implementation delegates to WordIterator and complexity is O(N^N) in place of O(N!)
  // 7! = 5040 | 7^7 = 823543
  // 8! = 40320 | 8^8 = 16777216
  private static final int MAX_PERM_SIZE = 7;

  @Test
  public void iterateOverAllPermutations() throws Exception {
    for (int n = 1; n <= MAX_PERM_SIZE; n++) {
      final int total = fact(n);
      assertThat(total).isEqualTo(count(n));
    }

  }

  private int count(final int n) {
    final PermutationIterator gen = new PermutationIterator(n);
    int cpt = 0;
    for (final Perm perm : gen) {
      cpt++;
    }
    return cpt;
  }

  private int fact(final int n) {
    if (n == 0) {
      return 1;
    }
    return n * fact(n - 1);
  }
}
