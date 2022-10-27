package com.duosec.duosecbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
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
public class CompanyCreds {
    ObjectId id;

    private String companyName;
    private String companyEmailId;
    private int otpRefreshDuration;
    private String algorithm;
    private String password;
    private String companyUniqueId;
    private String createDate;
    private String expireDate;
    private boolean companyMailVerified;
}
