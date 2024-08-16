/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.Utill;

/**
 *
 * @author pinal
 */
public class DecimalToWordsConverter {

    private static final String[] units = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static final String[] teens = {"", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static final String[] tens = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    private static final String[] thousands = {"", "thousand", "million"};

    public static String convertToWords(double amount) {
        
        String val = String.valueOf(amount);
        String[] splitval = val.split("\\.");
        //int integerPart = (int) amount;
        //int decimalPart = (int) ((amount - integerPart) * 100); // Assuming two decimal places
        
        int integerPart = Integer.parseInt(splitval[0]);
        int decimalPart = Integer.parseInt(splitval[1]); // Assuming two decimal places

        String integerWords = convert(integerPart);
        String decimalWords = convert(decimalPart);

        StringBuilder result = new StringBuilder();
        result.append("").append(integerWords);

        if (decimalPart > 0) {
            result.append(" and ").append(decimalWords).append(" NGWEE");
        }

        return result.toString().toUpperCase();
    }

    private static String convert(int number) {
        if (number < 10) {
            return units[number];
        } else if (number < 20) {
            return teens[number - 10];
        } else if (number < 100) {
            return tens[number / 10] + " " + convert(number % 10);
        } else if (number < 1000) {
            return units[number / 100] + " hundred " + convert(number % 100);
        } else {
            int scale = 0;
            String result = "";

            while (number > 0) {
                int chunk = number % 1000;
                if (chunk > 0) {
                    result = convert(chunk) + " " + thousands[scale] + " " + result;
                }
                number /= 1000;
                scale++;
            }

            return result.trim();
        }
    }

    public static void main(String[] args) {
        double amount = 123456789.12;
        String words = convertToWords(amount);
        System.out.println(words);
    }
}



