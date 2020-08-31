package com.aeris.datavalidationrest.instruments;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


public interface InstrumentDao extends MongoRepository<Instrument, String>  {

    Optional<Instrument> findById(@PathVariable String id);
    void deleteById(@PathVariable String id);
    List<Instrument> findByResponsibleIdContains(@PathVariable String responsibleId);
}
