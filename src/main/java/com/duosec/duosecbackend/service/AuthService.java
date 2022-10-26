package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dto.CompanyRegister;
import com.duosec.duosecbackend.model.CompanyCreds;
import com.duosec.duosecbackend.utils.UniqueNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Avinash Vijayvargiya
 * Date: 25-Oct-22
 * Time: 11:16 AM
 */
@Service
public class AuthService {

    @Autowired
    private AuthModel authModel;

    public void saveRegisterCompanyDetails(CompanyRegister companyRegister) {
        CompanyCreds companyCreds = new CompanyCreds();
        UniqueNumber uniqueNumber = new UniqueNumber();
        companyCreds.setCompanyName(companyRegister.getCompanyName());
        companyCreds.setCompanyEmailId(companyRegister.getCompanyEmailId());
        companyCreds.setCompanyUniqueId(uniqueNumber.unique());
        authModel.save(companyCreds);
    }
}
