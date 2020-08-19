package com.aeris.datavalidationrest.instruments;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface InstrumentDao extends MongoRepository<Instrument, String>  {

    Instrument findByUuid(@PathVariable String uuid);

    List<Instrument> findByResponsibleIdContains(@PathVariable String responsibleId);

    void deleteById(@PathVariable String id);
}
