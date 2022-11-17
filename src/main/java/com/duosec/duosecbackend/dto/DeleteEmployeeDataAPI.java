package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 17-Nov-22
 * Time: 7:18 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteEmployeeDataAPI {
    private String apiKey;
    private String employeeId;
}
