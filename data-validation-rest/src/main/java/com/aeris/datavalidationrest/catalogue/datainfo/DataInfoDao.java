package com.aeris.datavalidationrest.catalogue.datainfo;

import com.aeris.datavalidationrest.flags.Flag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataInfoDao extends MongoRepository<DataInfo, String> {
    public Flag findByProgramName(String programName);
}

