package com.aeris.datavalidationrest.instruments;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InstrumentDao extends MongoRepository<Instrument, String>  {

}
