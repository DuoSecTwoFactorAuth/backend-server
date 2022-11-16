package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dto.ApiKeyRequest;
import com.duosec.duosecbackend.dto.ChangePasswordModel;
import com.duosec.duosecbackend.model.CompanyCreds;
import com.duosec.duosecbackend.utils.RandomOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * User: Avinash Vijayvargiya
 * Date: 09-Nov-22
 * Time: 10:59 PM
 */
@Service
public class SettingService {
    @Autowired
    private AuthModel authModel;

    public boolean changePassword(ChangePasswordModel model) {
        return updatePassword(model.getCompanyUniqueId(), model.getNewPassword(), model.getOldPassword());
    }

    private boolean updatePassword(String companyUniqueId, String newPassword, String oldPassword) {
        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(companyUniqueId).get();
        if (Objects.equals(companyCreds.getPassword(), oldPassword)) {
            companyCreds.setPassword(newPassword);
            authModel.save(companyCreds);
//            mailService.sendEmail(companyCreds.getCompanyEmailId(), companyCreds.getCompanyName(), Constants.CHANGE_PASSWORD_SUBJECT, Constants.HELLO + Constants.CHANGE_PASSWORD_BODY);
            return true;
        }
        return false;
    }

    public String getApiKey(ApiKeyRequest apiKeyRequest) {
        if (apiKeyRequest.isGenerateApiKey()) {
            CompanyCreds companyCreds = authModel.findByCompanyUniqueId(apiKeyRequest.getCompanyUniqueId()).get();
            companyCreds.setApiKey(new RandomOTP().generateApiKey());
            authModel.save(companyCreds);
            return companyCreds.getApiKey();
        }
        return authModel.findByCompanyUniqueId(apiKeyRequest.getCompanyUniqueId()).get().getApiKey();
    }
}
