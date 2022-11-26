package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 24-Nov-22
 * Time: 12:17 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardAllData {
    private String employeeId;
    private String name;
    private String emailId;
    private String contactNumber;
}
