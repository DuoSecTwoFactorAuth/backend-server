package com.duosec.duosecbackend.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 23-Oct-22
 * Time: 12:19 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser {
    private String username;
    private String password;
    private String role;
}
