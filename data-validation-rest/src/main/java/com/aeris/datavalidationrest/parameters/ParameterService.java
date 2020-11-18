package com.aeris.datavalidationrest.parameters;

import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.common.CommonService;
import com.aeris.datavalidationrest.netcdf.NetCdf;
import com.aeris.datavalidationrest.netcdf.NetCdfService;
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
    private NetCdfService netCdfService;
    @Autowired
    private CommonService commonService;

    static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static String FOLDER = "/GROUND-BASED/P2OA_Pic-Du-Midi/NEPHE/NEPHE_RAW/2019&image=PDM_NEPH_20190517.csv";
    public static final String SEDOO_BASED_DATA_URL = "https://sedoo.aeris-data.fr/actris-datacenter-rest/rest/quicklook/download?uuid=";

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    public Optional<Parameter> getParametById(String id) {
        return paramerDao.findById(id);
    }

    public  ResponseEntity<NetCdf> getParameterByName(String parameterName) {
        return netCdfService.getParameterByName(parameterName);
    }

    public Parameter getParameterDataByPeriod(String parameterName, String strStartDate, String strEndDate) {
        Parameter response = null;

        try {
            this.startDate = this.commonService.strToDate(strStartDate, DEFAULT_DATE_FORMAT);
            this.endDate = this.commonService.strToDate(strEndDate, DEFAULT_DATE_FORMAT);
            response = this.paramerDao.findByName(parameterName);
            if(response != null) {
                response.setParameterData(response.getParameterData().stream()
                        .filter(s -> s.getDate().after(startDate) && s.getDate().before(endDate))
                        .collect(Collectors.toList()));
            }
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
        List<Object> parametersSet;
        String url, data;
        ResponseEntity<String> response;

        if(uuid != null) {
            url = SEDOO_BASED_DATA_URL + uuid + "&folder=" + FOLDER;
            data = restTemplate.getForObject( url, String.class);
            parametersSet = this.parse(data);
            //this.insertParameters(parametersSet);
            response = ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Imported parameters");
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
        }
        return response;
    }

    public List<Object> parse(String data) {
        Reader reader;
        CsvToBean csvToBean;
        List<Object> parametersSet = null;

        if(data != null) {
            data = data.replace("\uFEFF", "");
            reader = new StringReader(data);
            csvToBean = new CsvToBeanBuilder<Object>(reader)
                    .withType(Object.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            parametersSet = csvToBean.parse();
        }

        return parametersSet;
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
