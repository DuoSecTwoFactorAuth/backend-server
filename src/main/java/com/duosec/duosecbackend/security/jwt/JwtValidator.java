package com.duosec.duosecbackend.security.jwt;

import com.duosec.duosecbackend.security.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

/**
 * User: Avinash Vijayvargiya
 * Date: 22-Oct-22
 * Time: 6:44 PM
 */
@Component
public class JwtValidator {

    public JwtUser validate(String token) {

        JwtUser jwtUser = null;

        try {
            String secret = "tolusecret";
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new JwtUser();
            jwtUser.setUsername(body.getSubject());
            jwtUser.setRole((String) body.get("password"));
            jwtUser.setRole((String) body.get("role"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jwtUser;
    }
}
