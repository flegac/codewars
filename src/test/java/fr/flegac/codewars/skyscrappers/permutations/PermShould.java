package fr.flegac.codewars.skyscrappers.permutations;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class PermShould {

  @Test
  public void matchOtherWithEqualsMethod() throws Exception {
    final Perm p1 = new Perm(2, 3, 5, 4, 6, 1, 0);
    final Perm p2 = new Perm(2, 3, 5, 4, 6, 1, 0);
    final Perm p3 = new Perm(2, 3, 5, 5, 6, 1, 0);

    assertThat(p1).isEqualTo(p2);
    assertThat(p1).isNotEqualTo(p3);
  }

  @Test
  public void provideNiceToString() throws Exception {
    final Perm p1 = new Perm(2, 3, 5, 4, 6, 1, 0);

    final String expected = "[2, 3, 5, 4, 6, 1, 0]";

    assertThat(p1.toString()).isEqualTo(expected);
  }

  @Test
  public void reverseOrder() throws Exception {
    final int size = 6;

    final Perm p1 = new Perm(0, 1, 2, 3, 4, 5);
    final Perm p2 = new Perm(0, 1, 2, 3, 4, 5);

    p2.reverse();

    for (int i = 0; i < size; i++) {
      assertThat(p1.get(i)).isNotEqualTo(p2.get(i));
      assertThat(p1.get(i)).isEqualTo(p2.get(size - 1 - i));
    }

  }

}
