package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 10-Dec-22
 * Time: 2:31 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegenerateJwtTokenRequest {
    private String companyHex;
}
