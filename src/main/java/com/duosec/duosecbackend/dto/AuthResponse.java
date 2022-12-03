package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 09-Nov-22
 * Time: 4:42 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private String companyUniqueId;
    private String token;
}
