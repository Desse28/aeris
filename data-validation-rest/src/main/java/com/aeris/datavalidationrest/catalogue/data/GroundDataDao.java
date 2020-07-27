package com.aeris.datavalidationrest.catalogue.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroundDataDao extends MongoRepository<GroundData, String> {
    public GroundData findByDateTime(String dateTime);
}
