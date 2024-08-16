/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.Utill;

/**
 *
 * @author pinal
 */
public class SplitStringNearWhitespaceExample {

    public static void main(String[] args) {
        String inputString = "ONE HUNDRED FIFTY TWO THOUSAND FIVE HUNDRED EIGHTY SIX AND TWENTY CENTS";

        if (inputString.length() > 45) {
            int splitIndex = findNearestWhitespace(inputString, 45);

            if (splitIndex != -1) {
                String part1 = inputString.substring(0, splitIndex).trim();
                String part2 = inputString.substring(splitIndex).trim();

                System.out.println("String is longer than 45 characters. Splitting into two parts:");
                System.out.println("Part 1: " + part1);
                System.out.println("Part 2: " + part2);
            } else {
                System.out.println("Couldn't find a suitable whitespace to split the string.");
            }
        } else {
            System.out.println("String is not longer than 45 characters.");
        }
    }

    private static int findNearestWhitespace(String input, int index) {
        int leftIndex = index;
        int rightIndex = index;

        // Move left to find whitespace
        while (leftIndex > 0 && !Character.isWhitespace(input.charAt(leftIndex))) {
            leftIndex--;
        }

        // Move right to find whitespace
        while (rightIndex < input.length() && !Character.isWhitespace(input.charAt(rightIndex))) {
            rightIndex++;
        }

        // Choose the nearest whitespace
        if (leftIndex > 0 && rightIndex < input.length()) {
            return (index - leftIndex) < (rightIndex - index) ? leftIndex : rightIndex;
        } else if (leftIndex > 0) {
            return leftIndex;
        } else if (rightIndex < input.length()) {
            return rightIndex;
        }

        return -1; // No whitespace found
    }
}

