package com.duosec.duosecbackend.controller;

import com.duosec.duosecbackend.dto.*;
import com.duosec.duosecbackend.model.CompanyCreds;
import com.duosec.duosecbackend.service.AuthService;
import com.duosec.duosecbackend.utils.Endpoints;
import com.duosec.duosecbackend.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User: Avinash Vijayvargiya
 * Date: 25-Oct-22
 * Time: 11:05 AM
 */

@RestController
@RequestMapping(Endpoints.AUTH)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/company-register")
    public ResponseEntity<String> companyRegister(@RequestBody CompanyRegister companyRegister) {
        try {
            authService.saveRegisterCompanyDetails(companyRegister);
//            TODO: Mail send service with unique ID
            return new ResponseEntity<>("Data Saved", HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Data Not Saved", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/get-company-details")
    public ResponseEntity<?> companyRegistration(@RequestParam String uniqueId) {
        try {
            CompanyCreds companyCreds = authService.getCompanyDetails(uniqueId);
            return new ResponseEntity<>(new CompanyRegister(companyCreds.getCompanyName(), companyCreds.getCompanyEmailId()), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/store-company-details")
    public ResponseEntity<String> storeCompanyDetails(@RequestBody CompanyRegisterComplete companyRegisterComplete) {
        try {
            if (authService.storeDetails(companyRegisterComplete))
                return new ResponseEntity<>("Data Stored", HttpStatus.OK);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Mail already verified");
            return new ResponseEntity<>(errorResponse.toString(), HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CompanyLogin companyLogin) {
        try {
            String response = authService.loginSentMail(companyLogin);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-login")
    public ResponseEntity<AuthResponse> verifyLogin(@RequestBody CompanyLoginVerify companyLoginVerify) {
        try {
            return new ResponseEntity<>(authService.verifyOtp(companyLoginVerify), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            String response = authService.forgotPassword(email);
            if (!response.startsWith("Invalid")) {
//                response = Constants.FRONTEND_URL + "?token=" + response;
                return new ResponseEntity<>("Token Sent" + response, HttpStatus.OK);
            }
            return new ResponseEntity<>("Company Not Found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String password) {
        try {
            return new ResponseEntity<>(authService.resetPassword(token, password), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("", HttpStatus.NOT_MODIFIED);
        }
    }
}
