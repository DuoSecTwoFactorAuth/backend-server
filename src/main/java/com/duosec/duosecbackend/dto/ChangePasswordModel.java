package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 09-Nov-22
 * Time: 11:00 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordModel {
    private String companyUniqueId;
    private String oldPassword;
    private String newPassword;
}
