package com.aeris.datavalidationrest.instruments;
import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.common.CommonService;
import com.aeris.datavalidationrest.common.RuntimeAnnotations;
import com.aeris.datavalidationrest.parameters.Parameter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InstrumentService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommonService commonService;

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    private static String FOLDER = "/GROUND-BASED/P2OA_Pic-Du-Midi/NEPHE/NEPHE_RAW/2019&image=PDM_NEPH_20190517.csv";
    public static final String SEDOO_BASED_DATA_URL = "https://sedoo.aeris-data.fr/actris-datacenter-rest/rest/quicklook/download?uuid=";


    public void getParameterData(String parameterName, String uuid) {
        Reader reader;
        List<Parameter> parameters;
        String url = SEDOO_BASED_DATA_URL + uuid + "&folder=" + FOLDER;
        String data = restTemplate.getForObject( url, String.class);
        data = data.replace("\uFEFF", "");
        reader = new StringReader(data);

        /*CsvToBean csvToBean = new CsvToBeanBuilder<Parameter>(reader)
                .withType(Parameter.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();*/
        //parameters = csvToBean.parse();
        logger.info("Test data");
        logger.info(data);
        logger.info("Test reader");
        logger.info(reader.toString());
    }


}
