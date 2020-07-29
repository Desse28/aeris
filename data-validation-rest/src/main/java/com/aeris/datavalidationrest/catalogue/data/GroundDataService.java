package com.aeris.datavalidationrest.catalogue.data;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

@Service
public class GroundDataService {
    public static final String SEDOO_GROUND_DATA_URL = "https://sedoo.aeris-data.fr/actris-datacenter-rest/rest/quicklook/download?uuid=91440f71-9c3e-5d31-befc-2729873ce581&folder=/GROUND-BASED/P2OA_Pic-Du-Midi/NEPHE/NEPHE_RAW/2019&image=PDM_NEPH_20190517.csv";

    public List<GroundData> getGroundData() {
        RestTemplate restTemplate = new RestTemplate();
        Reader reader;
        List<GroundData> groundDataList;
        String data = restTemplate.getForObject( SEDOO_GROUND_DATA_URL, String.class);

        data = data.replace("\uFEFF", "");
        reader = new StringReader(data);

        CsvToBean csvToBean = new CsvToBeanBuilder<GroundData>(reader)
                .withType(GroundData.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        groundDataList = csvToBean.parse();

        System.out.println("Test groundData : ");

        for(GroundData groundData : groundDataList) {
            System.out.println(groundData);
        }

        return groundDataList;
    }
}
