package com.aeris.datavalidationrest.parameters;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParamerDao extends MongoRepository<Parameter, String> {
    Parameter findByName(String name);
}
