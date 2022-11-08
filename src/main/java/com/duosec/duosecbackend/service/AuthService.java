package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dto.CompanyRegister;
import com.duosec.duosecbackend.dto.CompanyRegisterComplete;
import com.duosec.duosecbackend.model.CompanyCreds;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * User: Avinash Vijayvargiya
 * Date: 25-Oct-22
 * Time: 11:16 AM
 */
@Service
public class AuthService {

    @Autowired
    private AuthModel authModel;

    public CompanyCreds getCompanyDetails(String uniqueId) {
        return authModel.findByCompanyUniqueId(uniqueId).get();
    }


    public ObjectId saveRegisterCompanyDetails(CompanyRegister companyRegister) {
        CompanyCreds companyCreds = new CompanyCreds();
        companyCreds.setCreateDate(LocalDateTime.now());
        companyCreds.setExpireDate(LocalDateTime.now().plusMinutes(1));
        companyCreds.setCompanyName(companyRegister.getCompanyName());
        companyCreds.setCompanyEmailId(companyRegister.getCompanyEmailId());
        authModel.save(companyCreds);
        companyCreds.setCompanyUniqueId(companyCreds.getId().toString());
        authModel.save(companyCreds);
        return companyCreds.getId();
    }

    public boolean storeDetails(CompanyRegisterComplete companyRegisterComplete) {
        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(companyRegisterComplete.getCompanyUniqueId()).get();
        if (companyCreds.getCompanyUniqueId().equals(companyRegisterComplete.getCompanyUniqueId()) && !companyCreds.isCompanyMailVerified()) {
            companyCreds.setAlgorithm(companyRegisterComplete.getAlgorithm());
            companyCreds.setOtpRefreshDuration(companyRegisterComplete.getOtpRefreshDuration());
            companyCreds.setPassword(companyRegisterComplete.getPassword());
            companyCreds.setCompanyMailVerified(true);
            authModel.save(companyCreds);
            return true;
        }
        return false;
    }
}
