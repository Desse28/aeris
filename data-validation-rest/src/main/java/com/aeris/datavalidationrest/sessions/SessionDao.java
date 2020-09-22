package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.parameters.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface SessionDao extends MongoRepository<Session, String> {

    void deleteById(String id);
    Optional<Session> findById(String id);
    @Query(value="{ 'piId' : ?0 }")
    Page<List<Session>> findAllByPiId(@PathVariable String piId, Pageable pageable);
    boolean existsByInstrumentNameAndMainParameterAndLinkedParameters(String instrumentName, Parameter mainParameter, List<Parameter> linkedParameters);
}
