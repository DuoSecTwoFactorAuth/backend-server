package com.duosec.duosecbackend.controller;

import com.duosec.duosecbackend.dto.CompanyRegister;
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
            Object uniqueId = authService.saveRegisterCompanyDetails(companyRegister);
            System.out.println(uniqueId);
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
            if (companyCreds != null) {
                return new ResponseEntity<>(new CompanyRegister(companyCreds.getCompanyName(), companyCreds.getCompanyEmailId()), HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Mail already verified");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
