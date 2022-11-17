package com.duosec.duosecbackend.controller;

import com.duosec.duosecbackend.dto.AddEmployeeData;
import com.duosec.duosecbackend.dto.AddEmployeeDataAPI;
import com.duosec.duosecbackend.dto.DeleteEmployeeData;
import com.duosec.duosecbackend.dto.DeleteEmployeeDataAPI;
import com.duosec.duosecbackend.service.DashboardService;
import com.duosec.duosecbackend.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/add-employee-from -ui")
    public ResponseEntity<String> addEmployee(@RequestBody AddEmployeeData addEmployeeData) {
        try {
            return new ResponseEntity<>(dashboardService.addEmployee(addEmployeeData), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(errorResponse.toString(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestBody AddEmployeeDataAPI addEmployeeDataAPI) {
        try {
            return new ResponseEntity<>(dashboardService.addEmployee(addEmployeeDataAPI), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(errorResponse.toString(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete-employee-from -ui")
    public ResponseEntity<String> deleteEmployeeFromUi(@RequestBody DeleteEmployeeData deleteEmployeeData) {
        try {
            return new ResponseEntity<>(dashboardService.deleteEmployee(deleteEmployeeData), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(errorResponse.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete-employee")
    public ResponseEntity<String> deleteEmployee(@RequestBody DeleteEmployeeDataAPI deleteEmployeeDataAPI) {
        try {
            return new ResponseEntity<>(dashboardService.deleteEmployee(deleteEmployeeDataAPI), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(errorResponse.toString(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
