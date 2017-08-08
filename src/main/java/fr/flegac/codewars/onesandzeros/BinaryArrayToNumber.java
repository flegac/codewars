package fr.flegac.codewars.onesandzeros;

import java.util.List;

/**
 *
 * for binary (b_n, b_{n-1}, ..., b_0) :
 * result = b_n * 2^n + b_{n-1} * 2^{n-1} + ... + b_0 * 2^0
 *
 * is equivalent to the expression :
 * result = b_0 + 2 * ( b_1 + 2 * ( .... + 2 * ( b_{n-1} + 2 * b_n ) ... ) )
 *
 * Algorithm :
 *
 * int result = 0;
 * for(Integer bit : binary) {
 * result = result * 2 + bit;
 * }
 * return result;
 *
 */
public class BinaryArrayToNumber {

  public static int ConvertBinaryArrayToInt(final List<Integer> binary) {
    return binary.stream().reduce((r, e) -> 2 * r + e).get();
  }
}
