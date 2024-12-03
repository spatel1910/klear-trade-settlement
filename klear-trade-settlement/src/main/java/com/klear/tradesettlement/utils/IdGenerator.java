package com.klear.tradesettlement.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class IdGenerator {

 public static String generateTradeId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy"); // Shorten year to two digits
        String datePart = LocalDateTime.now().format(formatter); // Format date
        long milliseconds = System.currentTimeMillis() % 100000; // Get last 5 digits of milliseconds
        int randomPart = new Random().nextInt(100); // Random number from 0 to 99

        // Concatenate and ensure the total length is 15
        return datePart + String.format("%05d", milliseconds) + String.format("%02d", randomPart);
    }
   public static String generateClientId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd"); // Shorten year to two digits
        String datePart = LocalDateTime.now().format(formatter); // Format date
        long milliseconds = System.currentTimeMillis() % 1000000; // Get last 5 digits of milliseconds
        int randomPart = new Random().nextInt(1000); // Random number from 0 to 99

        // Concatenate and ensure the total length is 15
        return datePart + String.format("%05d", milliseconds) + String.format("%02d", randomPart);
    }

}
