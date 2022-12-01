package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dto.ApiKeyRequest;
import com.duosec.duosecbackend.dto.ChangePasswordModel;
import com.duosec.duosecbackend.exception.DataException;
import com.duosec.duosecbackend.exception.EmptyDataException;
import com.duosec.duosecbackend.exception.NullDataException;
import com.duosec.duosecbackend.model.CompanyCreds;
import com.duosec.duosecbackend.utils.ExtensionFunction;
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

    public Boolean changePassword(ChangePasswordModel changePasswordModel) throws EmptyDataException, NullDataException, DataException {
        ExtensionFunction extensionFunction = new ExtensionFunction();
        if (extensionFunction.isNull(changePasswordModel.getNewPassword()) ||
                extensionFunction.isNull(changePasswordModel.getOldPassword()) ||
                extensionFunction.isNull(changePasswordModel.getCompanyUniqueId()))
            throw new EmptyDataException("ChangePasswordModel values can't be Null");

        if (changePasswordModel.getNewPassword().isEmpty() ||
                changePasswordModel.getOldPassword().isEmpty() ||
                changePasswordModel.getCompanyUniqueId().isEmpty())
            throw new EmptyDataException("ChangePasswordModel values can't be Empty");

        return updatePassword(changePasswordModel.getCompanyUniqueId(), changePasswordModel.getNewPassword(), changePasswordModel.getOldPassword());
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

    public String getApiKey(ApiKeyRequest apiKeyRequest) throws NullDataException, EmptyDataException, DataException {
        ExtensionFunction extensionFunction = new ExtensionFunction();
        if (extensionFunction.isNull(apiKeyRequest.getCompanyUniqueId()))
            throw new NullDataException("CompanyUniqueId can't be Null");

        if (apiKeyRequest.getCompanyUniqueId().isEmpty())
            throw new EmptyDataException("CompanyUniqueId can't be Empty");

        try {
            if (apiKeyRequest.isGenerateApiKey()) {
                CompanyCreds companyCreds = authModel.findByCompanyUniqueId(apiKeyRequest.getCompanyUniqueId()).get();
                companyCreds.setApiKey(new RandomOTP().generateApiKey());
                authModel.save(companyCreds);
//            TODO: Mail Service (API Key changed)
                return companyCreds.getApiKey();
            }
            return authModel.findByCompanyUniqueId(apiKeyRequest.getCompanyUniqueId()).get().getApiKey();
        } catch (DataException dataException) {
            throw new DataException("Can't get API Key");
        }
    }
}
