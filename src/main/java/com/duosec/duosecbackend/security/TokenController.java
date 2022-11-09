package com.duosec.duosecbackend.security;

import com.duosec.duosecbackend.security.jwt.JwtGenerator;
import com.duosec.duosecbackend.security.model.JwtUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * User: Avinash Vijayvargiya
 * Date: 09-Nov-22
 * Time: 5:06 PM
 */
public class TokenController {

    private final JwtGenerator jwtGenerator;

    public TokenController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping()
    public String generate(@RequestBody final JwtUser jwtUser) {
        return jwtGenerator.generate(jwtUser);
    }
}
