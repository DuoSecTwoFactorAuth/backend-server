package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 17-Nov-22
 * Time: 1:10 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployeeDataAPI {
    private String companyApiKey;
    private String employeeId;
    private String name;
    private String emailId;
    private String phoneNumber;
}
