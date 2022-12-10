package com.duosec.duosecbackend.utils;

import com.bastiaanjansen.jwt.JWT;
import com.bastiaanjansen.jwt.algorithms.Algorithm;
import com.bastiaanjansen.jwt.exceptions.JWTCreationException;

import java.util.Date;

/**
 * User: Avinash Vijayvargiya
 * Date: 28-Nov-22
 * Time: 4:37 PM
 */
public class CreateJwtToken {
    public String createJwt(byte[] secret, int otpRefreshDuration, String algorithm, String companyName, String employeeUniqueIdHex) {
        Algorithm jwtAlgorithm = Algorithm.HMAC512("DUOSEC");
        try {
            return new JWT.Builder(jwtAlgorithm)
                    .withClaim("secret", secret)
                    .withClaim("otpRefreshDuration", otpRefreshDuration)
                    .withClaim("algorithm", algorithm)
                    .withClaim("companyName", companyName)
                    .withClaim("employeeUniqueIdHex", employeeUniqueIdHex)
                    .withIssuedAt(new Date())
                    .withExpirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6))
                    .sign();
        } catch (JWTCreationException jwtCreationException) {
            return jwtCreationException.getMessage();
        }
    }
}
