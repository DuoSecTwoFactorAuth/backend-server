package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 25-Oct-22
 * Time: 11:09 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRegister {
    private String companyName;
    private String companyEmailId;
}
