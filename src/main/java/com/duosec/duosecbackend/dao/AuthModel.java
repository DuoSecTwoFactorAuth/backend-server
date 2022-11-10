package com.duosec.duosecbackend.dao;

import com.duosec.duosecbackend.model.CompanyCreds;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthModel extends MongoRepository<CompanyCreds, Integer> {
    @Query("{companyUniqueId :  ?0}")
    Optional<CompanyCreds> findByCompanyUniqueId(String uniqueId);

    @Query("{companyEmailId :  ?0}")
    Optional<CompanyCreds> findByCompanyEmailId(String companyEmailId);

    @Query("{token :  ?0}")
    CompanyCreds findByToken(String token);
}
