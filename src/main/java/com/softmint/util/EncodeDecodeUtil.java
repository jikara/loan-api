package com.softmint.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeDecodeUtil {

    /**
     * Encodes the provided data into a URL-safe Base64 format.
     *
     * @param userId the user ID
     * @param token  the token
     * @return the encoded Base64 string
     */
    public static String encode(String userId, String token) {
        String data = userId + ":" + token; // Combine userId and token
        return Base64.getUrlEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodes the provided Base64 string back into user ID and token.
     *
     * @param encodedData the encoded Base64 string
     * @return an array containing user ID and token, or null if decoding fails
     */
    public static String[] decode(String encodedData) {
        try {
            String decodedData = new String(Base64.getUrlDecoder().decode(encodedData), StandardCharsets.UTF_8);
            return decodedData.split(":"); // Split into userId and token
        } catch (IllegalArgumentException e) {
            // Handle decoding failure (e.g., invalid Base64)
            return null;
        }
    }
}

