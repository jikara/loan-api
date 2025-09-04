package com.softmint.util;

import java.math.BigInteger;
import java.util.UUID;


public class ReferenceGeneratorUtil {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Base36
    private static final int BASE = ALPHABET.length();

    public static String generateReference(UUID uuid) {
        // Use the least significant bits for compactness
        BigInteger value = BigInteger.valueOf(uuid.getLeastSignificantBits());
        // Make positive
        if (value.signum() < 0) {
            value = value.negate();
        }
        StringBuilder sb = new StringBuilder();
        while (value.compareTo(BigInteger.ZERO) > 0 && sb.length() < 8) {
            int remainder = value.mod(BigInteger.valueOf(BASE)).intValue();
            sb.append(ALPHABET.charAt(remainder));
            value = value.divide(BigInteger.valueOf(BASE));
        }
        // Pad if shorter than 8
        while (sb.length() < 8) {
            sb.append('X');
        }
        return sb.reverse().toString();
    }
}
