package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dao.DashboardModel;
import com.duosec.duosecbackend.dto.AddEmployeeData;
import com.duosec.duosecbackend.dto.AddEmployeeDataAPI;
import com.duosec.duosecbackend.dto.DeleteEmployeeData;
import com.duosec.duosecbackend.dto.DeleteEmployeeDataAPI;
import com.duosec.duosecbackend.model.CompanyEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Avinash Vijayvargiya
 * Date: 16-Nov-22
 * Time: 6:03 PM
 */
@Service
public class DashboardService {

    @Autowired
    private DashboardModel dashboardModel;

    @Autowired
    private AuthModel authModel;


    public String addEmployee(AddEmployeeData addEmployeeData) {
        CompanyEmployee companyEmployee = new CompanyEmployee(addEmployeeData.getCompanyUniqueId(), addEmployeeData.getEmployeeId(), addEmployeeData.getName(), addEmployeeData.getEmailId(), addEmployeeData.getPhoneNumber(), System.currentTimeMillis());
        dashboardModel.save(companyEmployee);
        return "Data Saved";
    }

    public String addEmployee(AddEmployeeDataAPI addEmployeeDataAPI) {
        String companyUniqueId = authModel.findByApiKey(addEmployeeDataAPI.getCompanyApiKey()).getCompanyUniqueId();
        CompanyEmployee companyEmployee = new CompanyEmployee(companyUniqueId, addEmployeeDataAPI.getEmployeeId(), addEmployeeDataAPI.getName(), addEmployeeDataAPI.getEmailId(), addEmployeeDataAPI.getPhoneNumber(), System.currentTimeMillis());
        dashboardModel.save(companyEmployee);
        return "Data Saved";
    }

    public String deleteEmployee(DeleteEmployeeData deleteEmployeeData) {
        dashboardModel.deleteByEmployeeIdAAndCompanyUniqueId(deleteEmployeeData.getEmployeeId(), deleteEmployeeData.getCompanyUniqueId());
        return "Data Deleted";
    }

    public String deleteEmployee(DeleteEmployeeDataAPI deleteEmployeeDataAPI) {
        String companyUniqueId = authModel.findByApiKey(deleteEmployeeDataAPI.getApiKey()).getCompanyUniqueId();
        dashboardModel.deleteByEmployeeIdAAndCompanyUniqueId(deleteEmployeeDataAPI.getEmployeeId(), companyUniqueId);
        return "Data Deleted";
    }

    public Map<String, Object> getAllEmployee(String companyUniqueId, String employeeName, int page, int size, String sortBy, Boolean sort) {
        List<CompanyEmployee> tutorials;
        Pageable paging = PageRequest.of(page, size);

        Page<CompanyEmployee> companyEmployeePage;
        if (employeeName == null)
            companyEmployeePage = dashboardModel.findAllByCompanyUniqueId(companyUniqueId, paging);
        else
            companyEmployeePage = dashboardModel.findAllByNameAndCompanyUniqueId(employeeName, companyUniqueId, paging);

        tutorials = companyEmployeePage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", tutorials);
        response.put("currentPage", companyEmployeePage.getNumber());
        response.put("totalItems", companyEmployeePage.getTotalElements());
        response.put("totalPages", companyEmployeePage.getTotalPages());

        return response;
    }
}
