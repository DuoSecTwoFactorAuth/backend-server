package com.duosec.duosecbackend.service;

import com.duosec.duosecbackend.dao.AuthModel;
import com.duosec.duosecbackend.dao.DashboardModel;
import com.duosec.duosecbackend.dto.*;
import com.duosec.duosecbackend.exception.DataException;
import com.duosec.duosecbackend.exception.EmptyDataException;
import com.duosec.duosecbackend.exception.NullDataException;
import com.duosec.duosecbackend.model.CompanyCreds;
import com.duosec.duosecbackend.model.CompanyEmployee;
import com.duosec.duosecbackend.utils.CreateJwtToken;
import com.duosec.duosecbackend.utils.ExtensionFunction;
import org.apache.commons.codec.digest.DigestUtils;
import org.duosec.backendlibrary.HMACAlgorithm;
import org.duosec.backendlibrary.SecretGenerator;
import org.duosec.backendlibrary.TOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


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

    public void addEmployee(AddEmployeeData addEmployeeData) {
        byte[] secret = SecretGenerator.generate();
        LocalDateTime date = LocalDateTime.now();
        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(addEmployeeData.getCompanyUniqueId()).get();
        String employeeUniqueIdHex = DigestUtils.sha256Hex(companyCreds.getCompanyUniqueId() + addEmployeeData.getEmployeeId());
        CreateJwtToken createJwtToken = new CreateJwtToken();
        String jwtToken = createJwtToken.createJwt(secret, companyCreds.getOtpRefreshDuration(),
                companyCreds.getAlgorithm());
        CompanyEmployee companyEmployee = new CompanyEmployee(
                addEmployeeData.getCompanyUniqueId(),
                addEmployeeData.getEmployeeId(),
                addEmployeeData.getName(),
                addEmployeeData.getEmailId(),
                addEmployeeData.getPhoneNumber(),
                date, secret, employeeUniqueIdHex, jwtToken);
        dashboardModel.save(companyEmployee);
    }

    public String addEmployee(AddEmployeeDataAPI addEmployeeDataAPI) {
        byte[] secret = SecretGenerator.generate();
        LocalDateTime date = LocalDateTime.now();
        String companyUniqueId = authModel.findByApiKey(addEmployeeDataAPI.getCompanyApiKey()).getCompanyUniqueId();
        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(companyUniqueId).get();
        String employeeUniqueIdHex = DigestUtils.sha256Hex(companyCreds.getCompanyUniqueId() + addEmployeeDataAPI.getEmployeeId());
        CreateJwtToken createJwtToken = new CreateJwtToken();
        String jwtToken = createJwtToken.createJwt(secret, companyCreds.getOtpRefreshDuration(),
                companyCreds.getAlgorithm());
        CompanyEmployee companyEmployee = new CompanyEmployee(
                companyUniqueId,
                addEmployeeDataAPI.getEmployeeId(),
                addEmployeeDataAPI.getName(),
                addEmployeeDataAPI.getEmailId(),
                addEmployeeDataAPI.getPhoneNumber(),
                date,
                secret, employeeUniqueIdHex, jwtToken);
        dashboardModel.save(companyEmployee);
        return "Data Saved";
//        TODO Send mail to the user
    }

    public String deleteEmployee(DeleteEmployeeData deleteEmployeeData) throws NullDataException, EmptyDataException, DataException {
        ExtensionFunction extensionFunction = new ExtensionFunction();
        if (extensionFunction.isNull(deleteEmployeeData.getEmployeeId()) || extensionFunction.isNull(deleteEmployeeData.getCompanyUniqueId()))
            throw new EmptyDataException("Delete Employee Can't be Null");
        if (deleteEmployeeData.getEmployeeId().isEmpty() || deleteEmployeeData.getCompanyUniqueId().isEmpty())
            throw new EmptyDataException("Delete Employee Can't be Empty");

        try {
            dashboardModel.deleteByEmployeeIdAAndCompanyUniqueId(deleteEmployeeData.getEmployeeId(), deleteEmployeeData.getCompanyUniqueId());
            return "Data Deleted";
        } catch (DataException dataException) {
            throw new DataException("Data Not Deleted");
        }
    }

    public String deleteEmployee(DeleteEmployeeDataAPI deleteEmployeeDataAPI) throws NullDataException, EmptyDataException, DataException {
        ExtensionFunction extensionFunction = new ExtensionFunction();
        if (extensionFunction.isNull(deleteEmployeeDataAPI.getApiKey()) || extensionFunction.isNull(deleteEmployeeDataAPI.getEmployeeId()))
            throw new EmptyDataException("Delete Employee Can't be Null");
        if (deleteEmployeeDataAPI.getApiKey().isEmpty() || deleteEmployeeDataAPI.getEmployeeId().isEmpty())
            throw new EmptyDataException("Delete Employee Can't be Empty");

        try {
            String companyUniqueId = authModel.findByApiKey(deleteEmployeeDataAPI.getApiKey()).getCompanyUniqueId();
            dashboardModel.deleteByEmployeeIdAAndCompanyUniqueId(deleteEmployeeDataAPI.getEmployeeId(), companyUniqueId);
            return "Data Deleted";
        } catch (DataException dataException) {
            throw new DataException("Data Not Saved");
        }
    }

    public Map<String, Object> getAllEmployee(String companyUniqueId, String employeeName, int page, int size, String sortBy, boolean sort) throws NullDataException, EmptyDataException {
        List<DashboardAllData> dashboardAllDataList = new ArrayList<>();
        Page<CompanyEmployee> companyEmployeePage;
        Pageable paging;

        ExtensionFunction extensionFunction = new ExtensionFunction();
        if (extensionFunction.isNull(companyUniqueId))
            throw new NullDataException("Null Company Unique Id");

        if (companyUniqueId.isEmpty())
            throw new EmptyDataException("Empty Company Unique Id");

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

    public String getQrData(String companyEmployeeHash) {
        CompanyEmployee companyEmployee = dashboardModel.findByEmployeeUniqueIdHex(companyEmployeeHash);
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        if (Duration.between(today, companyEmployee.getSecretTime()).toMillis() > 0)
            return null;
        return companyEmployee.getEmployeeUniqueIdHex();
    }

    public Boolean verifyTOTP(String companyId, String employeeId, String totp) {
        CompanyCreds companyCreds = authModel.findByCompanyUniqueId(companyId).get();
        byte[] secret = dashboardModel.findByEmployeeIdAndCompanyUniqueId(employeeId, companyId).get().getSecret();

        TOTP.Builder builder = new TOTP.Builder(secret);
        String algorithm = companyCreds.getAlgorithm();

        HMACAlgorithm algo;
        if (Objects.equals(algorithm, "SHA1"))
            algo = HMACAlgorithm.SHA1;
        else if (Objects.equals(algorithm, "SHA256"))
            algo = HMACAlgorithm.SHA256;
        else
            algo = HMACAlgorithm.SHA512;

        builder
                .withPasswordLength(6)
                .withAlgorithm(algo)
                .withPeriod(Duration.ofSeconds(companyCreds.getOtpRefreshDuration() * 60L));

        TOTP totpObj = builder.build();

        return totpObj.verify(totp, 2);
    }
}
