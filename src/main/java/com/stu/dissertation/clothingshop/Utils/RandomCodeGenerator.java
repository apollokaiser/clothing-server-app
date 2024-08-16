package com.stu.dissertation.clothingshop.Utils;

import java.security.SecureRandom;
import java.util.Random;

public class RandomCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String ORDERCODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
    public static String generateOrderCode() {
        SecureRandom random = new SecureRandom();
        Long thisTime = System.currentTimeMillis() /1000;
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(ORDERCODE.length());
            sb.append(ORDERCODE.charAt(index));
        }
        sb.append(thisTime);
        return sb.toString();
    }

}
