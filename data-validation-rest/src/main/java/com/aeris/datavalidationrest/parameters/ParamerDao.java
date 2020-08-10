package com.aeris.datavalidationrest.parameters;

import com.aeris.datavalidationrest.instruments.Instrument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParamerDao extends MongoRepository<Instrument, String>  {

}
