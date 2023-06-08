package org.example.restaurant;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainTest {

    @Test
    public void testHashPassword() {
        String password = "myPassword123";
        String hashedPassword = PasswordUtils.hashPassword(password);

        Assertions.assertNotNull(hashedPassword);
        Assertions.assertNotEquals(password, hashedPassword);
    }

    @Test
    public void testBytesToHex() {
        byte[] bytes = {(byte) 10, (byte) 15, (byte) 0, (byte) 255};
        String expectedHex = "0a0f00ff";

        String actualHex = PasswordUtils.bytesToHex(bytes);

        Assertions.assertEquals(expectedHex, actualHex);
    }


    @Test
    public void testVerifyPassword() {
        String password = "myPassword123";
        String hashedPassword = PasswordUtils.hashPassword(password);

        // Test when the passwords match
        boolean result = PasswordUtils.verifyPassword(password, hashedPassword);
        Assertions.assertTrue(result);

        // Test when the passwords do not match
        String wrongPassword = "wrongPassword";
        boolean result2 = PasswordUtils.verifyPassword(wrongPassword, hashedPassword);
        Assertions.assertFalse(result2);

        // Test when hashedInput is null
        boolean result3 = PasswordUtils.verifyPassword(password, null);
        Assertions.assertFalse(result3);

        // Test when both hashedInput and hashedPassword are null
        boolean result4 = PasswordUtils.verifyPassword(null, null);
        Assertions.assertFalse(result4);
    }
}

class PasswordUtils {

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return hashedPassword != null && hashedPassword.equals(hashPassword(password));
    }
}