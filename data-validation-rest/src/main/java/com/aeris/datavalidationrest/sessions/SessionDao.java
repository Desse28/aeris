package com.aeris.datavalidationrest.sessions;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionDao extends MongoRepository<Session, String> {
    public Session findByUserId(String userId);
}
