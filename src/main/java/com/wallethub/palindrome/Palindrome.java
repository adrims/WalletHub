package com.wallethub.palindrome;

/**
 * Class for check if a string is a palindrome
 *
 * @author Adrián Martín Sánchez
 */
public class Palindrome {

    //String to check
    private String stringToCheck;

    //Constructor
    public Palindrome(String stringToCheck) {
        this.stringToCheck = stringToCheck;
    }

    /**
     * Method for check if a string is processable
     * @return True if stringToCheck is not null or it has more than one character , false otherwise
     */
    private Boolean isStringProcessable() {
        return (this.stringToCheck != null && this.stringToCheck.length() > 1);
    }

    /**
     * Method for check if a string is a palindrome
     * @return True if stringToCheck is a palindrome, false otherwise
     */
    public boolean isPalindrome() {
        if (isStringProcessable()) {
            int i = 0;
            int j = stringToCheck.length() - 1;
            while (i < j) {
                if (stringToCheck.charAt(i) != stringToCheck.charAt(j)) {
                    return false;
                }
                i++;
                j--;
            }
            return true;
        }
        return false;
    }

    /**
     * Method for check if a string is a palindrome with ignore case
     * @return True if stringToCheck is a palindrome, false otherwise
     */
    public boolean isPalindromeIgnoreCase() {
        if (isStringProcessable()) {
            int i = 0;
            int j = stringToCheck.length() - 1;
            while (i < j) {
                if (Character.toLowerCase(stringToCheck.charAt(i)) !=
                        Character.toLowerCase(stringToCheck.charAt(j))) {
                    return false;
                }
                i++;
                j--;
            }
            return true;
        }
        return false;
    }
}
