package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 08-Dec-22
 * Time: 4:18 PM
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecoveryCodeResponse {
    private String recoveryCode;
}
