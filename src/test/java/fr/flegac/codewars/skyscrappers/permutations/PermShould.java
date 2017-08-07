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

}
