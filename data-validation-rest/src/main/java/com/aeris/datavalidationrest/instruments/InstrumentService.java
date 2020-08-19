package com.aeris.datavalidationrest.instruments;
import com.aeris.datavalidationrest.auth.LoginResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Reader;
import java.util.*;

@Service
public class InstrumentService {
    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    private static String FOLDER = "/GROUND-BASED/P2OA_Pic-Du-Midi/NEPHE/NEPHE_RAW/2019&image=PDM_NEPH_20190517.csv";
    public static final String SEDOO_BASED_DATA_URL = "https://sedoo.aeris-data.fr/actris-datacenter-rest/rest/quicklook/download?uuid=";


    public List<String> getParameterData(String parameterName, String uuid) {
        Reader reader;
        //List<Parameter> parameters;
        String url = SEDOO_BASED_DATA_URL + uuid + "&folder=" + FOLDER;
        String data = restTemplate.getForObject( url, String.class);
        data = data.replace("\uFEFF", "");

        //reader = new StringReader(data);
        /*CsvToBean csvToBean = new CsvToBeanBuilder<Parameter>(reader)
                .withType(Parameter.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();*/
        //parameters = csvToBean.parse();

        return parseData(parameterName, data);
    }

    public List<String> parseData(String parameterName, String data) {
        int columnIndex;
        List<String> result = new ArrayList<>();
        String[] dataLines = data.split("\n");
        List<String> columns = Arrays.asList(dataLines[0].split(","));

        if (columns.contains("\"" + parameterName + "\"")) {
            columnIndex = columns.indexOf("\"" + parameterName + "\"");

            for (String line : dataLines) {
                result.add(line.split(",")[columnIndex]);
            }
        }

        return result;
    }

}
