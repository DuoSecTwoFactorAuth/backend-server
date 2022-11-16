package com.duosec.duosecbackend.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * User: Avinash Vijayvargiya
 * Date: 08-Nov-22
 * Time: 4:32 PM
 */
public class RandomOTP {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public String generateApiKey() {
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
