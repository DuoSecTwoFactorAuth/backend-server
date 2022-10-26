package com.duosec.duosecbackend.dao;

import com.duosec.duosecbackend.model.CompanyCreds;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AuthModel extends MongoRepository<CompanyCreds, Integer> {

}
