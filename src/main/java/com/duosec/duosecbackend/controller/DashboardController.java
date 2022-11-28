package com.duosec.duosecbackend.controller;

import com.duosec.duosecbackend.dto.*;
import com.duosec.duosecbackend.exception.DataException;
import com.duosec.duosecbackend.exception.EmptyDataException;
import com.duosec.duosecbackend.exception.NullDataException;
import com.duosec.duosecbackend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            return new ResponseEntity<>(dashboardService.addEmployee(addEmployeeData), HttpStatus.ACCEPTED);
        } catch (NullDataException | EmptyDataException | DataException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_MODIFIED);
        } catch (RuntimeException runtimeException) {
            return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestBody AddEmployeeDataAPI addEmployeeDataAPI) {
        try {
            return new ResponseEntity<>(dashboardService.addEmployee(addEmployeeDataAPI), HttpStatus.ACCEPTED);
        } catch (NullDataException | EmptyDataException | DataException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_MODIFIED);
        } catch (RuntimeException runtimeException) {
            return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-employee-from -ui")
    public ResponseEntity<String> deleteEmployeeFromUi(@RequestBody DeleteEmployeeData deleteEmployeeData) {
        try {
            return new ResponseEntity<>(dashboardService.deleteEmployee(deleteEmployeeData), HttpStatus.ACCEPTED);
        } catch (NullDataException | EmptyDataException | DataException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_MODIFIED);
        } catch (RuntimeException runtimeException) {
            return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete-employee")
    public ResponseEntity<String> deleteEmployee(@RequestBody DeleteEmployeeDataAPI deleteEmployeeDataAPI) {
        try {
            return new ResponseEntity<>(dashboardService.deleteEmployee(deleteEmployeeDataAPI), HttpStatus.ACCEPTED);
        } catch (NullDataException | EmptyDataException | DataException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_MODIFIED);
        } catch (RuntimeException runtimeException) {
            return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-all-employee")
    public ResponseEntity<?> getAllEmployee(@RequestBody PaginationData paginationData) {
        try {
            Map<String, Object> objectMap = dashboardService.getAllEmployee(paginationData.getCompanyUniqueId(), paginationData.getEmployeeName(), paginationData.getPage(), paginationData.getSize(), paginationData.getSortBy(), paginationData.isSort());
            return new ResponseEntity<>(objectMap, HttpStatus.OK);
        } catch (NullDataException exception) {
            throw new NullDataException("Data can't be Null");
        } catch (EmptyDataException exception) {
            throw new EmptyDataException("Data can't be Empty");
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
