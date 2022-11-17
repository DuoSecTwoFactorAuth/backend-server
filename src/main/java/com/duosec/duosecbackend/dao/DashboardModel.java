package com.duosec.duosecbackend.dao;


import com.duosec.duosecbackend.model.CompanyEmployee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardModel extends MongoRepository<CompanyEmployee, String> {
    @Query(value = "{employeeId :  ?0}", delete = true)
    void deleteByEmployeeId(String employeeId);
}
