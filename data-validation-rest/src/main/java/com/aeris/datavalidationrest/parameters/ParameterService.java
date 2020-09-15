package com.aeris.datavalidationrest.parameters;

import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.catalogue.data.GroundData;
import com.aeris.datavalidationrest.common.CommonService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParameterService {
    Date startDate;
    Date endDate;
    @Autowired
    private ParamerDao paramerDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommonService commonService;

    private static String FOLDER = "/GROUND-BASED/P2OA_Pic-Du-Midi/NEPHE/NEPHE_RAW/2019&image=PDM_NEPH_20190517.csv";
    public static final String SEDOO_BASED_DATA_URL = "https://sedoo.aeris-data.fr/actris-datacenter-rest/rest/quicklook/download?uuid=";

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    public Optional<Parameter> getParametById(String id) {
        return paramerDao.findById(id);
    }

    public Parameter getParameterDataByPeriod(String parameterName, String strStartDate, String strEndDate) {
        Parameter response = null;
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

        try {
            this.startDate = this.commonService.strToDate(strStartDate, dateFormat);
            this.endDate = this.commonService.strToDate(strEndDate, dateFormat);
            response = this.paramerDao.findByName(parameterName);
            response.setParameterData(response.getParameterData().stream()
                    .filter(s -> s.getDate().after(startDate) && s.getDate().before(endDate))
                    .collect(Collectors.toList()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }

    public ResponseEntity<Parameter> insertParameter(Parameter parameter) {
        Parameter parameterAdded;
        ResponseEntity<Parameter> response;

        if(this.commonService.isAdmin(request)) {
            parameterAdded = paramerDao.save(parameter);
            response = getCreateResponse(parameterAdded);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(null);
        }

        return response;
    }

    public ResponseEntity<String> updateParameter(Parameter parameter) {
        ResponseEntity<String> response;

        if(this.commonService.isAdmin(request)) {
            this.paramerDao.save(parameter);
            response = ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Update parameter");
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
        }

        return response;
    }

    public ResponseEntity<String> importParameters(String uuid) {
        List<GroundData> parametersSet;
        String url, data;
        ResponseEntity<String> response;

        if(uuid != null) {
            url = SEDOO_BASED_DATA_URL + uuid + "&folder=" + FOLDER;
            data = restTemplate.getForObject( url, String.class);
            parametersSet = this.parse(data);
            this.insertParameters(parametersSet);
            response = ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Imported parameters");
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
        }
        return response;
    }

    public List<GroundData> parse(String data) {
        Reader reader;
        CsvToBean csvToBean;
        List<GroundData> parametersSet = null;

        if(data != null) {
            data = data.replace("\uFEFF", "");
            reader = new StringReader(data);
            csvToBean = new CsvToBeanBuilder<GroundData>(reader)
                    .withType(GroundData.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            parametersSet = csvToBean.parse();
        }

        return parametersSet;
    }

    public ResponseEntity<List<ResponseEntity<Parameter>>> insertParameters(List<GroundData> parametersSet) {
        ResponseEntity<List<ResponseEntity<Parameter>>> response;
        List<ResponseEntity<Parameter>> parametersAdded = new ArrayList<>();

        if(this.commonService.isAdmin(request)) {
            for(GroundData parameters : parametersSet) {
                this.insertParameterData(parameters);
            }
            response =  ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(parametersAdded);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(parametersAdded);
        }

        return response;
    }

    public void insertParameterData(GroundData parameters) {
        Date date;
        String dateTime;
        ParameterData parameterData;
        if(parameters != null) {
            dateTime = parameters.getDateTime();
            try {
                date = this.commonService.strToDate(dateTime, "yyyy/MM/dd hh:mm:ss");
                parameterData = new ParameterData();
                parameterData.setDate(date);
                parameterData.setValue(parameters.getZeroDegσspR());
                saveParameter(parameterData, "0°σspR");
                parameterData.setValue(parameters.getZeroDegσspG());
                saveParameter(parameterData, "0°σspG");
                parameterData.setValue(parameters.getZeroDegσspB());
                saveParameter(parameterData, "0°σspB");
                parameterData.setValue(parameters.getNinetyDegσspG());
                saveParameter(parameterData, "90°σspG");
                parameterData.setValue(parameters.getNinetyDegσspB());
                saveParameter(parameterData, "90°σspB");
                parameterData.setValue(parameters.getAirTemp());
                saveParameter(parameterData, "Air Temp");
                parameterData.setValue(parameters.getCellTemp());
                saveParameter(parameterData, "Cell Temp");
                parameterData.setValue(parameters.getrH());
                saveParameter(parameterData, "RH");
                parameterData.setValue(parameters.getPressure());
                saveParameter(parameterData, "Pressure");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveParameter(ParameterData parameterData, String currentParameterName) {
        Parameter currentParameter;
        if(this.paramerDao.existsByName(currentParameterName)) {
            currentParameter = this.paramerDao.findByName(currentParameterName);
            if(!this.paramerDao.existsByParameterDataContaining(parameterData)) {
                currentParameter.getParameterData().add(parameterData);
                this.updateParameter(currentParameter);
            }
        } else {
            currentParameter = new Parameter();
            currentParameter.setName(currentParameterName);
            currentParameter.setParameterData(new ArrayList<>());
            currentParameter.getParameterData().add(parameterData);
            this.insertParameter(currentParameter);
        }
    }

    public  ResponseEntity<Parameter> getCreateResponse(Parameter parameter) {
        URI location;
        ResponseEntity<Parameter> response;

        if (parameter != null) {
            location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(parameter.getId())
                    .toUri();
            response = ResponseEntity.created(location).body(parameter);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(null);
        }
        return response;
    }
}
