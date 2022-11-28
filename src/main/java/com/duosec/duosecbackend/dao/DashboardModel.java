package com.duosec.duosecbackend.dao;


import com.duosec.duosecbackend.model.CompanyEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardModel extends MongoRepository<CompanyEmployee, String> {
    @Query(value = "{$and :[{employeeId :  ?0}, {companyUniqueId: ?1}]}", delete = true)
    void deleteByEmployeeIdAAndCompanyUniqueId(String employeeId, String companyUniqueId);

    Page<CompanyEmployee> findAllByNameAndCompanyUniqueId(String name, String companyUniqueId, Pageable page);

    Page<CompanyEmployee> findAllByCompanyUniqueId(String companyUniqueId, Pageable pageable);

    CompanyEmployee findByEmployeeUniqueIdHex(String hash);
}
