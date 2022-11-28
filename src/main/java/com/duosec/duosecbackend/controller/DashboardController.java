package com.duosec.duosecbackend.controller;

import com.duosec.duosecbackend.dto.*;
import com.duosec.duosecbackend.service.DashboardService;
import com.duosec.duosecbackend.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            dashboardService.addEmployee(addEmployeeData);
            return new ResponseEntity<>("employee created", HttpStatus.ACCEPTED);
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

    @PostMapping("/get-all-employee")
    public ResponseEntity<?> getAllEmployee(@RequestBody PaginationData paginationData) {
        try {
            Map<String, Object> objectMap = dashboardService.getAllEmployee(paginationData.getCompanyUniqueId(), paginationData.getEmployeeName(), paginationData.getPage(), paginationData.getSize(), paginationData.getSortBy(), paginationData.isSort());
            return new ResponseEntity<>(objectMap, HttpStatus.OK);
        } catch (Exception ex) {
            if (ex.getMessage().equals("Null Company Unique Id"))
                return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_ACCEPTABLE);
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-qr-code")
    public ResponseEntity<String> getQrCodeData(@RequestParam String companyEmployeeHash) {
        try {
            return new ResponseEntity<>(dashboardService.getQrData(companyEmployeeHash), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("No Data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
