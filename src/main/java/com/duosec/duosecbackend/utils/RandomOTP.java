package com.duosec.duosecbackend.utils;

import java.util.Random;

/**
 * User: Avinash Vijayvargiya
 * Date: 08-Nov-22
 * Time: 4:32 PM
 */
public class RandomOTP {
    public String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
