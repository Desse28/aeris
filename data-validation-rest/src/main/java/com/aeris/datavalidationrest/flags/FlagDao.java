package com.aeris.datavalidationrest.flags;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlagDao extends MongoRepository<Flag, String> {
    public Flag findByCode(String flagCode);
}
