package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 26-Oct-22
 * Time: 7:39 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRegisterComplete {
    private int otpRefreshDuration;
    private String algorithm;
    private String password;
    private String companyUniqueId;
}
