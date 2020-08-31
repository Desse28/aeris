package com.aeris.datavalidationrest.instruments;
import com.aeris.datavalidationrest.auth.LoginResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InstrumentService {
    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    private static String FOLDER = "/GROUND-BASED/P2OA_Pic-Du-Midi/NEPHE/NEPHE_RAW/2019&image=PDM_NEPH_20190517.csv";
    public static final String SEDOO_BASED_DATA_URL = "https://sedoo.aeris-data.fr/actris-datacenter-rest/rest/quicklook/download?uuid=";


    public List<Map<String, String>> getParameterData(String parameterName, String uuid) {
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

    public List<Map<String, String>> parseData(String parameterName, String data) {
        String[] currentLine;
        String dateName = "Date_Time";
        Map<String, String> currentData;
        String targetColumn, dateColumn;
        int targetColumnIndex, dateColumnIndex;
        String[] dataLines = data.split("\n");
        String dateColumnName = "\"" + dateName + "\"";
        List<Map<String, String>> result = new ArrayList<>();
        String targetColumnName = "\"" + parameterName + "\"";
        List<String> columns = Arrays.asList(dataLines[0].split(",")).stream().map(String::trim).collect(Collectors.toList());

        if (columns.contains(targetColumnName)) {
            dateColumnIndex = columns.indexOf(dateColumnName);
            targetColumnIndex = columns.indexOf(targetColumnName);
            for (String line : dataLines) {
                currentData = new HashMap<>();
                currentLine = line.split(",");
                dateColumn =  currentLine[dateColumnIndex];
                if(!dateColumn.contains(dateName)) {
                    targetColumn = currentLine[targetColumnIndex].replace("\r","");
                    currentData.put(dateName, dateColumn);
                    currentData.put(parameterName, targetColumn);
                    result.add(currentData);
                }
            }
        }
        return result;
    }

}
