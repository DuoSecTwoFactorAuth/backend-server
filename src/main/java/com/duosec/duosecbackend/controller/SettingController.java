package com.duosec.duosecbackend.controller;

import com.duosec.duosecbackend.dto.ApiKeyRequest;
import com.duosec.duosecbackend.dto.ApiKeyResponse;
import com.duosec.duosecbackend.dto.ChangePasswordModel;
import com.duosec.duosecbackend.exception.CompanyNotFoundException;
import com.duosec.duosecbackend.exception.EmptyDataException;
import com.duosec.duosecbackend.exception.NullDataException;
import com.duosec.duosecbackend.service.SettingService;
import com.duosec.duosecbackend.utils.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Avinash Vijayvargiya
 * Date: 09-Nov-22
 * Time: 10:58 PM
 */
@RestController
@RequestMapping(Endpoints.SETTINGS)
public class SettingController {

    @Autowired
    private SettingService settingService;

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordModel changePasswordModel) {
        try {
            if (settingService.changePassword(changePasswordModel))
                return new ResponseEntity<>(HttpStatus.OK);
            throw new CompanyNotFoundException("Company not found");
        } catch (NullDataException | EmptyDataException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_MODIFIED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-api-key")
    public ResponseEntity<ApiKeyResponse> getApiKey(@RequestBody ApiKeyRequest apiKeyRequest) {
        try {
            return new ResponseEntity<>(new ApiKeyResponse(settingService.getApiKey(apiKeyRequest)), HttpStatus.OK);
        } catch (NullDataException nullDataException) {
            throw new NullDataException("ApiKeyRequest can't be Null");
        } catch (EmptyDataException emptyDataException) {
            throw new EmptyDataException("ApiKeyRequest can't be Null");
        } catch (RuntimeException runtimeException) {
            throw new RuntimeException();
        }
    }
}
