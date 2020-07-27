package com.aeris.datavalidationrest.catalogue.flag;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlagDao extends MongoRepository<Flag, String> {
    public Flag findByCode(String flagCode);
}
