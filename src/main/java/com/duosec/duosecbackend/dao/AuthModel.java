package com.duosec.duosecbackend.dao;

import com.duosec.duosecbackend.model.CompanyCreds;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthModel extends MongoRepository<CompanyCreds, Integer> {
    Optional<CompanyCreds> findByCompanyUniqueId(String uniqueId);

    Optional<CompanyCreds> findByCompanyEmailId(String companyEmailId);
}
