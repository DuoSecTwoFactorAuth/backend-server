package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 10-Dec-22
 * Time: 12:31 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecoveryCodeData {
    private String recoveryCode;
    private String apiKey;
    private String employeeID;
}
