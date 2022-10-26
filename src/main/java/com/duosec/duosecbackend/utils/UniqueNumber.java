package com.duosec.duosecbackend.utils;

import java.security.SecureRandom;

/**
 * User: Avinash Vijayvargiya
 * Date: 25-Oct-22
 * Time: 11:54 AM
 */
public class UniqueNumber {
    private static final long MSB = 0x8000000000000000L;
    private static volatile SecureRandom numberGenerator = null;

    public String unique() {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
    }
}
