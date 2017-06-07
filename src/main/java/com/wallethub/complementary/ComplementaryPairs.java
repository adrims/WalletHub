package com.wallethub.complementary;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for get the k-complementary pairs
 *
 * @author Adrián Martín Sánchez
 */
public class ComplementaryPairs {

    private static final int MIN_LENGTH = 2;

    /**
     * Get a list of Pair for given K and array of numbers.
     *
     * @param numberArray Array of numbers
     * @param k       K value
     * @return A set of Pair with K-commentaries
     */
    public static Set<Pair> getComplementaryPairs(int[] numberArray, int k) {
        Set<Pair> pairs = new HashSet<>(); // Use a Set for avoid repeated results
        // Check if numberArray has at least 2 numbers
        if (numberArray != null && numberArray.length >= MIN_LENGTH) {

            // In java 8 Arrays.sort has a O(nlogn) complexity
            Arrays.sort(numberArray);
            int j = numberArray.length - 1;
            int i = 0;

            /*
            * It is possible use this loop, because numberArray was sorted before
            * If found k-complementary pair add to set
            * If the sum of this pair is lower only increase left iterator (searching for higher number)
            * Otherwise decrease right iterator (searching for lower number)
            */
            while (i < j ) {
                if (numberArray[i] + numberArray[j] == k) {
                    pairs.add(new Pair(numberArray[i], numberArray[j]));
                    i++;
                    j--;
                } else if (numberArray[i] + numberArray[j] < k) {
                    i++; //
                } else {
                    j--;
                }
            }
        }
        return pairs;
    }
}

/**
 * Class for pair values of K-complementary.
 *
 * @author Adrián Martín
 */
class Pair {
    private int firstNumber;
    private int secondNumber;

    // Contructor
    public Pair(int firstNumber, int secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    // Override equals & hashcode for using Set<Pair> and avoid repeated results
    @Override
    public boolean equals(Object object) {

        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair)object;
        return (this.firstNumber == pair.firstNumber && this.secondNumber == pair.secondNumber);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = firstNumber;
        result = (int) (temp ^ (temp >>> 32));
        temp = secondNumber;
        result += (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format("{%s,%s}",firstNumber,secondNumber);
    }
}

