package com.wallethub.complementary;

import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * UI for ComplementaryPairs
 *
 * @author Adrián Martín Sánchez
 */
public class ComplementaryPairsIT {

    private final String expectedResult             = "[{1,3}]";
    private final String expectedResultRepeated     = "[{1,3}, {2,2}]";
    private final String checking                   = "Checking %s, with k = %s";
    private final String error                      = "Error testing ComplementaryPairs class, method getComplementaryPairs";
    private final int k                             = 4;
    private final int[] numberArray                 = {1,2,3,4,5};
    private final int[] numberArrayRepeated         = {1,1,2,2,3,3,4,4,5,5};
    private final int[] numberArrayNoSort           = {5,2,1,4,3};
    private final int[] numberArrayNoSortRepeated   = {2,5,2,1,3,1,2,1,4,3,1,3,2};



    @Test
    public void getComplementaryPairsTest () {
        try {
            assertEquals(String.format(checking, Arrays.toString(numberArray), k),
                   expectedResult,  ComplementaryPairs.getComplementaryPairs(numberArray, k).toString());
            assertEquals(String.format(checking, Arrays.toString(numberArrayRepeated), k),
                    expectedResultRepeated, ComplementaryPairs.getComplementaryPairs(numberArrayRepeated, k).toString());
            assertEquals(String.format(checking, Arrays.toString(numberArrayNoSort), k),
                    expectedResult, ComplementaryPairs.getComplementaryPairs(numberArrayNoSort, k).toString());
            assertEquals(String.format(checking, Arrays.toString(numberArrayNoSortRepeated), k),
                    expectedResultRepeated, ComplementaryPairs.getComplementaryPairs(numberArrayNoSortRepeated, k).toString());
        } catch (Exception e) {
        assertTrue(error.concat(e.getMessage()), false);
    }
    }
}
