package com.aeris.datavalidationrest.instruments;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface InstrumentDao extends MongoRepository<Instrument, String>  {

    Instrument findByUuid(@PathVariable String uuid);
}
