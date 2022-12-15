package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 17-Nov-22
 * Time: 1:51 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyEmployeeIdentifier {
    private String companyUniqueId;
    private String employeeId;
}
