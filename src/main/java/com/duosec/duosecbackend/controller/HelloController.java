package com.duosec.duosecbackend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.duosec.duosecbackend.utils.Constants.BEARER_AUTH;

/**
 * User: Avinash Vijayvargiya
 * Date: 22-Oct-22
 * Time: 1:31 PM
 */

@RestController
@SecurityRequirement(name = BEARER_AUTH)
public class HelloController {

    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    @GetMapping("/api/hello")
    public String sayHello(@RequestParam String name) {
        return "Hello " + name;
    }
}
