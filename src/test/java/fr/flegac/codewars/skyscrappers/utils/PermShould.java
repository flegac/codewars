package fr.flegac.codewars.skyscrappers.utils;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import fr.flegac.codewars.skyscrappers.permutations.Perm;

public class PermShould {

  @Test
  public void matchOtherWithEqualsMethod() throws Exception {
    final Perm p1 = new Perm(2, 3, 5, 4, 6, 1, 0);
    final Perm p2 = new Perm(2, 3, 5, 4, 6, 1, 0);

    assertThat(p1).isEqualTo(p2);
  }

  @Test
  public void provideNiceToString() throws Exception {
    final Perm p1 = new Perm(2, 3, 5, 4, 6, 1, 0);

    final String expected = "[2, 3, 5, 4, 6, 1, 0]";

    assertThat(p1.toString()).isEqualTo(expected);
  }

}
