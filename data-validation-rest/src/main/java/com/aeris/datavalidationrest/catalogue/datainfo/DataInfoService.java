package com.aeris.datavalidationrest.catalogue.datainfo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DataInfoService {
    public static final String DATA_INFO_URL = "https://sedoo.aeris-data.fr/catalogue/rest/metadatarecette/id/91440f71-9c3e-5d31-befc-2729873ce581";

    public DataInfo getDataInfo( RestTemplate restTemplate) {
        DataInfo dataInfo = restTemplate.getForObject( DATA_INFO_URL, DataInfo.class);
        return dataInfo;
    }
}
