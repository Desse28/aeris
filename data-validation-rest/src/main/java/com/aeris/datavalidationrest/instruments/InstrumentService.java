package com.aeris.datavalidationrest.instruments;
import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.catalogue.datainfo.DataInfoDao;
import com.aeris.datavalidationrest.common.CommonService;
import com.aeris.datavalidationrest.parameters.Parameter;
import com.aeris.datavalidationrest.parameters.ParameterService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;

@Service
public class InstrumentService {
    @Autowired
    private DataInfoDao dataInfoDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CommonService commonService;
    @Autowired
    private InstrumentDao instrumentDao;
    @Autowired
    private ParameterService parameterService;

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    public ResponseEntity<Optional<Instrument>> getByName(String name) {
        Optional<Instrument> instrument;
        ResponseEntity<Optional<Instrument>> response;

        if(this.commonService.isPI(request)) {
            instrument = this.instrumentDao.findByName(name);
            response = ResponseEntity.status(HttpStatus.SC_OK).body(instrument);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(null);
        }

        return response;
    }

    public ResponseEntity<Optional<Instrument>> getById(String id) {
        Optional<Instrument> instrument;
        ResponseEntity<Optional<Instrument>> response;

        if ( this.commonService.isPI(request)) {
            instrument = this.instrumentDao.findById(id);
            response = ResponseEntity.status(HttpStatus.SC_OK).body(instrument);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(null);
        }
        return response;
    }

    public Parameter getParameterDataByPeriod(String parameterName, String startDate, String endDate) {
            return this.parameterService.getParameterDataByPeriod(parameterName, startDate, endDate);
    }

    public ResponseEntity<List<String>> getAllNames() {
        ResponseEntity response;
        List<String> instrumentsNames = new ArrayList<>();
        String responsibleId = this.getResponsibleId();

        if(responsibleId != null) {
            instrumentsNames = instrumentDao.findAllByResponsibleIdContains(responsibleId);
            response = ResponseEntity.status(HttpStatus.SC_OK).body(instrumentsNames);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(instrumentsNames);
        }

        return response;
    }

    public ResponseEntity<List<Instrument>> getAll() {
        ResponseEntity<List<Instrument>> response;
        String responsibleId = this.getResponsibleId();
        List<Instrument> instruments = new ArrayList<>();

        if(responsibleId != null) {
            instruments = instrumentDao.findByResponsibleIdContains(responsibleId);
            response = ResponseEntity.status(HttpStatus.SC_OK).body(instruments);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(instruments);
        }
        return response;
    }

    public ResponseEntity<Instrument> insertNewInstrument(Instrument instrument) {
        Instrument instrumentAdded;
        ResponseEntity<Instrument> response;

        if (this.commonService.isAdmin(request)) {
            instrumentAdded = instrumentDao.save(instrument);
            response = getCreateResponse(instrumentAdded);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(null);
        }

        return response;
    }

    public ResponseEntity<String> updateInstrument(Instrument instrument) {
        ResponseEntity<String> response;

        if (this.commonService.isAdmin(request)) {
            instrumentDao.save(instrument);
            response = ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Update instrument");
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
        }

        return response;
    }

    public  ResponseEntity<String> deleteInstrument(String id) {
        ResponseEntity<String> response;

        if (this.commonService.isAdmin(request)) {
            instrumentDao.deleteById(id);
            return ResponseEntity.status(HttpStatus.SC_OK).body("Delete instrument");
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
        }

        return response;
    }

    public String getResponsibleId() {
        String responsibleId = null;

        if (this.commonService.isAdmin(request) || this.commonService.isPI(request)) {
            responsibleId = this.commonService.getCurrrentUserId(request);
        }

        return responsibleId;
    }

    public  ResponseEntity<Instrument> getCreateResponse(Instrument instrument) {
        URI location;
        ResponseEntity<Instrument> response;

        if (instrument != null) {
            location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(instrument.getId())
                    .toUri();
            response = ResponseEntity.created(location).body(instrument);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(null);
        }
        return response;
    }

    public ResponseEntity<String> importParameters(String uuid) {
        ResponseEntity<String> response = this.parameterService.importParameters(uuid);
        return response;
    }
}
