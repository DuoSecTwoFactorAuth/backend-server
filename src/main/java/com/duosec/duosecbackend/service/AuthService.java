package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dto.CompanyRegister;
import com.duosec.duosecbackend.model.CompanyCreds;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(uniqueId).get();
        if (!companyCreds.isCompanyMailVerified()) {
            companyCreds.setCompanyMailVerified(true);
            authModel.save(companyCreds);
            return companyCreds;
        }
        return null;
    }

    public ObjectId saveRegisterCompanyDetails(CompanyRegister companyRegister) {
        CompanyCreds companyCreds = new CompanyCreds();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        companyCreds.setCreateDate(sdf.format(c.getTime()));
        c.add(Calendar.DATE, 7);

        companyCreds.setExpireDate(sdf.format(c.getTime()));
        companyCreds.setCompanyName(companyRegister.getCompanyName());
        companyCreds.setCompanyEmailId(companyRegister.getCompanyEmailId());
        authModel.save(companyCreds);
        companyCreds.setCompanyUniqueId(companyCreds.getId().toString());
        authModel.save(companyCreds);
        return companyCreds.getId();
    }
}
