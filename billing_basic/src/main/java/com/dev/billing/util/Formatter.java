package com.dev.billing.util;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by vinay on 3/6/17.
 */
public class Formatter {

    private static String numberToWord(int n, String ch) {
        String one[] = {" ", " one", " two", " three", " four", " five", " six", " seven", " eight", " Nine", " ten", " eleven", " twelve",
                " thirteen", " fourteen", "fifteen", " sixteen", " seventeen", " eighteen", " nineteen"};

        String ten[] = {" ", " ", " twenty", " thirty", " forty", " fifty", " sixty", " seventy", " eighty", " ninety"};

        StringBuilder stringBuilder = new StringBuilder();
        if (n > 19) {
            stringBuilder.append(ten[n / 10] + "" + one[n % 10]);
        } else {
            stringBuilder.append(one[n]);
        }
        if (n > 0) stringBuilder.append(ch);
        return stringBuilder.toString();
    }

    public static String covertNumberToWord(int number) {
        StringBuilder builder = new StringBuilder();
        builder.append(numberToWord((number / 1000000000), " Hundred"))
                .append(numberToWord((number / 10000000) % 100, " crore"))
                .append(numberToWord(((number / 100000) % 100), " lakh"))
                .append(numberToWord(((number / 1000) % 100), " thousand"))
                .append(numberToWord(((number / 100) % 10), " hundred"))
                .append(numberToWord((number % 100), " "));
        return changeToTitleCase(builder.toString().trim());
    }

    public static String changeToTitleCase(String str) {
        String words[] = str.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1);
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            result.append(words[i]).append(" ");
        }
        return result.toString().trim();
    }

    public static String formatNumber(String number){
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(Double.parseDouble(number));

    }

    public static void main(String[] args) {
        System.out.println(covertNumberToWord(585974566));


    }
}
