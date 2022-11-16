package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.DashboardModel;
import com.duosec.duosecbackend.dto.AddEmployeeData;
import com.duosec.duosecbackend.model.CompanyEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Avinash Vijayvargiya
 * Date: 16-Nov-22
 * Time: 6:03 PM
 */
@Service
public class DashboardService {

    @Autowired
    private DashboardModel dashboardModel;


    public String addEmployee(AddEmployeeData addEmployeeData) {
        CompanyEmployee companyEmployee = new CompanyEmployee(
                addEmployeeData.getCompanyUniqueId(),
                addEmployeeData.getEmployeeId(),
                addEmployeeData.getName(),
                addEmployeeData.getEmailId(),
                addEmployeeData.getPhoneNumber());
        dashboardModel.save(companyEmployee);
        return "Data Saved";
    }
}
