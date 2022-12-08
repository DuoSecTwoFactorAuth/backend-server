package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 08-Dec-22
 * Time: 3:18 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecoveryCodeRequest {
    private String companyHexCode;
    private boolean generateRecoveryCode;
}
