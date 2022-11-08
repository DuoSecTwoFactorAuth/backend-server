package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 08-Nov-22
 * Time: 4:27 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyLogin {
    private String companyEmailId;
    private String password;
}
