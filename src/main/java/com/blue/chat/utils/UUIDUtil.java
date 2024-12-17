package com.blue.chat.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author 26020
 */
public class UUIDUtil {
    private UUIDUtil() {
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "").toUpperCase();
    }

    public static String getUUIDByRules(String rules) {
        StringBuilder generateRandStr = new StringBuilder();
        Random rand = new Random();
        int length = 32;

        for(int i = 0; i < length; ++i) {
            if (rules != null) {
                int rulePoint = rules.length();
                int randNum = rand.nextInt(rulePoint);
                generateRandStr.append(rules, randNum, randNum + 1);
            }
        }

        return generateRandStr.toString();
    }
}
