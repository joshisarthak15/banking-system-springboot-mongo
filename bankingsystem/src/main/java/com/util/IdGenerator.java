package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdGenerator {

    private static final Random random = new Random();

    // ------------------ Generate Account Number ------------------
    public static String generateAccountNumber(String holderName) {

        // Remove spaces and take first 3 letters in uppercase
        String namePart = holderName.replaceAll("\\s+", "").toUpperCase();

        if (namePart.length() >= 3) {
            namePart = namePart.substring(0, 3);
        } else {
            // pad if less than 3 characters
            while (namePart.length() < 3) {
                namePart += "X";
            }
        }

        // generate 4 random digits
        int numberPart = 1000 + random.nextInt(9000);

        return namePart + numberPart;
    }

    // ------------------ Generate Unique Transaction ID ------------------
    public static String generateTransactionId() {

        // timestamp for uniqueness
        String timePart = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        // 3 random digits
        int randomPart = 100 + random.nextInt(900);

        return "TXN-" + timePart + "-" + randomPart;
    }
}
