package com.aeris.datavalidationrest.catalogue.flag;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FlagService {
    public static final String SEDOO_FLAGS_DATA_URL = "https://sedoo.aeris-data.fr/actris-datacenter-rest/rest/quality/flags/91440f71-9c3e-5d31-befc-2729873ce581";

    public Flag[] getFlag() {
        RestTemplate restTemplate = new RestTemplate();
        Flag[] flags =  restTemplate.getForObject( SEDOO_FLAGS_DATA_URL, Flag[].class);
        System.out.println("Test flag service : ");
        for(Flag flag : flags) {
            System.out.println(flag);
        }
        return flags;
    }
}
