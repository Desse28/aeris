package com.aeris.datavalidationrest.sessions;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SessionDao extends MongoRepository<Session, String> {
    void deleteById(String id);
    List<Session> findByPiId(String piId);
}
