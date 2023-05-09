package com.milkbot.util.keyGen;

import java.util.Random;

public class PasskeyGenerater {
    /**
     * Generate a random string.
     */


    public static String generatePasskey() {
        String alphanumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder passkeyBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 21; i++) {
            int index = random.nextInt(alphanumeric.length());
            char randomChar = alphanumeric.charAt(index);
            passkeyBuilder.append(randomChar);
        }

        return passkeyBuilder.toString();
    }
}
