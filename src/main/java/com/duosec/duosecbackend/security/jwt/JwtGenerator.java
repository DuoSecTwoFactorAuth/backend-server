package com.duosec.duosecbackend.security.jwt;

import com.duosec.duosecbackend.security.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * User: Avinash Vijayvargiya
 * Date: 22-Oct-22
 * Time: 6:43 PM
 */
@Component
public class JwtGenerator {

    public String generate(JwtUser jwtUser) {
        Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
        claims.put("role", jwtUser.getRole());

        return Jwts.builder().
                setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6))
                .signWith(SignatureAlgorithm.HS256, "tolusecret")
                .setHeaderParam("Type", "JWT")
                .compact();
    }
}
