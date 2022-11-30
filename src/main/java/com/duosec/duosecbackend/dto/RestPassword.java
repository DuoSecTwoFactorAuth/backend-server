package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 29-Nov-22
 * Time: 12:56 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestPassword {
    private String token;
    private String password;
}
