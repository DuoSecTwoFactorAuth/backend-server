package com.duosec.duosecbackend.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User: Avinash Vijayvargiya
 * Date: 25-Oct-22
 * Time: 12:48 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("CompanyCreds")
public class Registration {
    private String companyName;
    private String companyEmail;
    private int otpRefreshDuration;
    private String algorithm;
    private String password;

}
