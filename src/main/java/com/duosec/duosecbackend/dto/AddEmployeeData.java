package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 16-Nov-22
 * Time: 6:01 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployeeData {
    private String companyUniqueId;
    private String employeeId;
    private String name;
    private String emailId;
    private String phoneNumber;
}
