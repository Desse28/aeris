package com.aeris.datavalidationrest.sessions;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionDao extends MongoRepository<Session, String> {
    Session findByPiId(String piId);
}
