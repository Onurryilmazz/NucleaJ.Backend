package com.nuclea.common.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class for generating random keys and codes.
 */
public final class KeyGenerator {

    private static final String NUMBERS = "0123456789";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new SecureRandom();

    private KeyGenerator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Generate random numeric code.
     */
    public static String generateNumericCode(int length) {
        return generateRandomString(NUMBERS, length);
    }

    /**
     * Generate random alphanumeric code.
     */
    public static String generateAlphanumericCode(int length) {
        return generateRandomString(ALPHANUMERIC, length);
    }

    /**
     * Generate 6-digit verification code.
     */
    public static String generateVerificationCode() {
        return generateNumericCode(6);
    }

    /**
     * Generate random string from character set.
     */
    private static String generateRandomString(String characterSet, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(characterSet.length());
            sb.append(characterSet.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Generate random integer between min and max (inclusive).
     */
    public static int generateRandomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
