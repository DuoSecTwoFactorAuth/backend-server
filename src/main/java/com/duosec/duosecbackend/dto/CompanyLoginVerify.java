package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 08-Nov-22
 * Time: 5:00 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyLoginVerify {
    private String companyUniqueId;
    private String otp;
}
