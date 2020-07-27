package com.aeris.datavalidationrest.catalogue.datainfo;

import com.aeris.datavalidationrest.catalogue.flag.Flag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataInfoDao extends MongoRepository<DataInfo, String> {
    public Flag findByProgramName(String programName);
}

