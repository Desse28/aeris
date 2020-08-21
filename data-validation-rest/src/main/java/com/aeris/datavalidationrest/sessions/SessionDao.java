package com.aeris.datavalidationrest.sessions;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SessionDao extends MongoRepository<Session, String> {
    void deleteById(String id);
    Optional<Session> findById(String id);
    List<Session> findByPiId(String piId);
    List<Session> findAllByPiIdAndAndState(String piId, boolean state);
}
