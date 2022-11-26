package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dao.DashboardModel;
import com.duosec.duosecbackend.dto.*;
import com.duosec.duosecbackend.model.CompanyEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Map<String, Object> getAllEmployee(String companyUniqueId, String employeeName, int page, int size, String sortBy, boolean sort) throws Exception {
        List<DashboardAllData> dashboardAllDataList = new ArrayList<>();
        Page<CompanyEmployee> companyEmployeePage;
        Pageable paging;

        if (companyUniqueId == null)
            throw new Exception("Null Company Unique Id");

        if (size < 10)
            size = 10;

        if (sortBy == null || sortBy.equals(""))
            sortBy = "createDate";
        if (!sort)
            paging = PageRequest.of(page, size, Sort.by(sortBy).descending());
        else
            paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());


        if (employeeName == null || employeeName.equals(""))
            companyEmployeePage = dashboardModel.findAllByCompanyUniqueId(companyUniqueId, paging);
        else
            companyEmployeePage = dashboardModel.findAllByNameAndCompanyUniqueId(employeeName, companyUniqueId, paging);

        List<CompanyEmployee> companyEmployeeList = companyEmployeePage.getContent();

        for (CompanyEmployee companyEmployee : companyEmployeeList) {
            dashboardAllDataList.add(new DashboardAllData(
                    companyEmployee.getEmployeeId(),
                    companyEmployee.getName(),
                    companyEmployee.getEmailId(),
                    companyEmployee.getPhoneNumber()
            ));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("employeeData", dashboardAllDataList);
        response.put("currentPage", companyEmployeePage.getNumber());
        response.put("totalEmployees", companyEmployeePage.getTotalElements());
        response.put("totalPages", companyEmployeePage.getTotalPages());

        return response;
    }
}
