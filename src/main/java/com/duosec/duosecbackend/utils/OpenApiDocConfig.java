package com.duosec.duosecbackend.utils;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static com.duosec.duosecbackend.utils.Constants.BEARER_AUTH;

/**
 * User: Avinash Vijayvargiya
 * Date: 09-Nov-22
 * Time: 4:54 PM
 */
@Configuration
@SecurityScheme(name = BEARER_AUTH, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiDocConfig {

}
