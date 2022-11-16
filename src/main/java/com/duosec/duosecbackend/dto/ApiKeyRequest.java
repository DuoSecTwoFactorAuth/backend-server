package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 16-Nov-22
 * Time: 11:19 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiKeyRequest {
    private String companyUniqueId;
    private boolean generateApiKey;
}
