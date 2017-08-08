package fr.flegac.codewars.onesandzeros;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;

public class BinaryArrayToNumberTest {
  @org.junit.Test
  public void convertBinaryArrayToInt() throws Exception {

    assertEquals(1, BinaryArrayToNumber.ConvertBinaryArrayToInt(new ArrayList<>(Arrays.asList(0, 0, 0, 1))));
    assertEquals(15, BinaryArrayToNumber.ConvertBinaryArrayToInt(new ArrayList<>(Arrays.asList(1, 1, 1, 1))));
    assertEquals(6, BinaryArrayToNumber.ConvertBinaryArrayToInt(new ArrayList<>(Arrays.asList(0, 1, 1, 0))));
    assertEquals(9, BinaryArrayToNumber.ConvertBinaryArrayToInt(new ArrayList<>(Arrays.asList(1, 0, 0, 1))));

  }

}