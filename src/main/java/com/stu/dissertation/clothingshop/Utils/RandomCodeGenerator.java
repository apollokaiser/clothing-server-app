package com.stu.dissertation.clothingshop.Utils;

import java.util.Random;

public class RandomCodeGenerator {
    public static String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
