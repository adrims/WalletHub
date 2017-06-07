package com.wallethub.palindrome;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * IT for palindrome class
 *
 * @author Adrián Martín Sánchez
 */

public class PalindromeIT {

    private Palindrome palindromeTrue;
    private Palindrome palindromeFalse;
    private Palindrome palindromeIgnoreCaseTrue;
    private Palindrome palindromeOneChar;
    private Palindrome palindromeSpecialChars;
    private Palindrome palindromeLong;
    private Palindrome palindromeNull;
    private Palindrome palindromeEmpty;

    private final String checking                    = "Checking ";
    private final String checkingNull                = "Checking null";
    private final String checkingEmptyString         = "Checking empty string";
    private final String errorIsPalindrome           = "Error testing Palindrome class, method isPalindrome: ";
    private final String errorIsPalindromeIgnoreCase = "Error testing Palindrome class, method isPalindromeIgnoreCase: ";
    private final String errorInitialize             = "Error initializing PalindromeTest class: ";
    private final String stringTrue                  = "aabaa";
    private final String stringFalse                 = "aabb";
    private final String stringIgnoreCaseTrue        = "Aabaa";
    private final String stringOneChar               = "a";
    private final String stringSpecialChars          = "♣♣aa♣♣";
    private final String stringLong                  = "abaabaabaabaabaabaabaabaabaabaabaaba" +
            "abaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaaba" +
            "abaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaaba" +
            "abaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaaba" +
            "abaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaaba" +
            "abaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaabaaba";


    @Before
    public void initialize() throws Exception{
        try {
            palindromeTrue              = new Palindrome(stringTrue);
            palindromeFalse             = new Palindrome(stringFalse);
            palindromeIgnoreCaseTrue    = new Palindrome(stringIgnoreCaseTrue);
            palindromeOneChar           = new Palindrome(stringOneChar);
            palindromeSpecialChars      = new Palindrome(stringSpecialChars);
            palindromeLong              = new Palindrome(stringLong);
            palindromeNull              = new Palindrome(null);
            palindromeEmpty             = new Palindrome("");
        } catch (Exception e) {
            throw new Exception(errorInitialize.concat(e.getMessage()));
        }
    }

    @Test
    public void isPalindromeTest() {
        try {
            assertTrue(checking.concat(stringTrue), palindromeTrue.isPalindrome());
            assertFalse(checking.concat(stringFalse), palindromeFalse.isPalindrome());
            assertFalse(checking.concat(stringIgnoreCaseTrue), palindromeIgnoreCaseTrue.isPalindrome());
            assertFalse(checking.concat(stringOneChar), palindromeOneChar.isPalindrome());
            assertTrue(checking.concat(stringSpecialChars), palindromeSpecialChars.isPalindrome());
            assertTrue(checking.concat(stringLong), palindromeLong.isPalindrome());
            assertFalse(checkingNull, palindromeNull.isPalindrome());
            assertFalse(checkingEmptyString, palindromeEmpty.isPalindrome());
        } catch (Exception e) {
            assertTrue(errorIsPalindrome.concat(e.getMessage()), false);
        }
    }

    @Test
    public void isPalindromeIgnoreCaseTest() {
        try {
            assertTrue(checking.concat(stringTrue), palindromeTrue.isPalindromeIgnoreCase());
            assertFalse(checking.concat(stringFalse), palindromeFalse.isPalindromeIgnoreCase());
            assertTrue(checking.concat(stringIgnoreCaseTrue), palindromeIgnoreCaseTrue.isPalindromeIgnoreCase());
            assertFalse(checking.concat(stringOneChar), palindromeOneChar.isPalindrome());
            assertTrue(checking.concat(stringSpecialChars), palindromeSpecialChars.isPalindromeIgnoreCase());
            assertTrue(checking.concat(stringLong), palindromeLong.isPalindromeIgnoreCase());
            assertFalse(checkingNull, palindromeNull.isPalindromeIgnoreCase());
            assertFalse(checkingEmptyString, palindromeEmpty.isPalindromeIgnoreCase());
        } catch (Exception e) {
            assertTrue(errorIsPalindromeIgnoreCase.concat(e.getMessage()), false);
        }
    }
}
