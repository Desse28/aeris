package com.aeris.datavalidationrest.instruments;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


public interface InstrumentDao extends MongoRepository<Instrument, String>  {
    void deleteById(@PathVariable String id);
    Optional<Instrument> findById(@PathVariable String id);
    Optional<Instrument> findByName(@PathVariable String name);
    @Query(value="{ 'responsibleId' : ?0 }",fields="{ 'name' : 1}")
    List<String> findAllByResponsibleIdContains(@PathVariable String responsibleId);
    List<Instrument> findByResponsibleIdContains(@PathVariable String responsibleId);
}
