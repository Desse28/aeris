package com.aeris.datavalidationrest.chart;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChartDao extends MongoRepository<Chart, String> {

}
