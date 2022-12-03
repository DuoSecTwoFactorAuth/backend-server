package com.duosec.duosecbackend.controller;

import com.duosec.duosecbackend.dto.*;
import com.duosec.duosecbackend.exception.CompanyNotFoundException;
import com.duosec.duosecbackend.exception.EmptyDataException;
import com.duosec.duosecbackend.exception.NullDataException;
import com.duosec.duosecbackend.service.AuthService;
import com.duosec.duosecbackend.utils.Endpoints;
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
        } catch (EmptyDataException | NullDataException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (RuntimeException runtimeException) {
            return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-company-details")
    public ResponseEntity<CompanyRegister> companyRegistration(@RequestParam String uniqueId) {
        try {
            return new ResponseEntity<>(authService.getCompanyDetails(uniqueId), HttpStatus.OK);
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
            return new ResponseEntity<>("Data Not Stored", HttpStatus.NOT_MODIFIED);
        } catch (EmptyDataException | NullDataException ex) {
            throw ex;
        } catch (RuntimeException runtimeException) {
            return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody CompanyLogin companyLogin) {
        try {
            System.out.println(companyLogin);
            return new ResponseEntity<>(authService.login(companyLogin), HttpStatus.ACCEPTED);
        } catch (NullDataException | EmptyDataException ex) {
            throw new NullDataException("Data can't be null or empty");
        } catch (RuntimeException runtimeException) {
            throw new RuntimeException(runtimeException.getMessage());
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
            throw new CompanyNotFoundException("Company not found");
        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();
            return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody RestPassword restPassword) {
        try {
            return new ResponseEntity<>(authService.resetPassword(restPassword.getToken(), restPassword.getPassword()), HttpStatus.ACCEPTED);
        } catch (NullDataException | EmptyDataException ex) {
            throw ex;
        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();
            return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
