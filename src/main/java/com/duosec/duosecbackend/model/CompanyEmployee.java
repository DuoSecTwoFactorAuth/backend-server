package com.duosec.duosecbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * User: Avinash Vijayvargiya
 * Date: 16-Nov-22
 * Time: 5:57 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("CompanyEmployee")
public class CompanyEmployee {
    private String companyUniqueId;
    private String employeeId;
    private String name;
    private String emailId;
    private String phoneNumber;
    private byte[] secret;
    private LocalDateTime secretTime;
    private String employeeUniqueIdHex;
    private String jwtToken;

    public CompanyEmployee(String companyUniqueId, String employeeId, String name, String emailId, String phoneNumber, LocalDateTime secretTime, byte[] secret, String employeeUniqueIdHex, String jwtToken) {
        this.companyUniqueId = companyUniqueId;
        this.employeeId = employeeId;
        this.name = name;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.secretTime = secretTime;
        this.secret = secret;
        this.employeeUniqueIdHex = employeeUniqueIdHex;
        this.jwtToken = jwtToken;
    }
}
