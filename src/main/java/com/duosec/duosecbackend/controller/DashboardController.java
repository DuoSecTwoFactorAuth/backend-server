package com.duosec.duosecbackend.controller;

import com.duosec.duosecbackend.dto.AddEmployeeData;
import com.duosec.duosecbackend.dto.AddEmployeeDataAPI;
import com.duosec.duosecbackend.service.DashboardService;
import com.duosec.duosecbackend.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Avinash Vijayvargiya
 * Date: 16-Nov-22
 * Time: 5:56 PM
 */

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PostMapping("/addEmployeeFromUI")
    public ResponseEntity<String> addEmployee(@RequestBody AddEmployeeData addEmployeeData) {
        try {
            return new ResponseEntity<>(dashboardService.addEmployee(addEmployeeData), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(errorResponse.toString(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody AddEmployeeDataAPI addEmployeeDataAPI) {
        try {
            return new ResponseEntity<>(dashboardService.addEmployee(addEmployeeDataAPI), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(errorResponse.toString(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
