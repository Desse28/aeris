package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.catalogue.datainfo.DataInfo;
import com.aeris.datavalidationrest.catalogue.datainfo.DataInfoDao;
import com.aeris.datavalidationrest.common.CommonService;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/instruments")
public class InstrumentResource {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private InstrumentDao instrumentDao;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private DataInfoDao dataInfoDao;

    private static final String NOT_ALLOWED_TO_ADD_INSTRUMENT = "You are not allowed to add an instrument";

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    @GetMapping("/names")
    public ResponseEntity<List<String>> findAllNames() {
        return instrumentService.getAllNames(request);
    }

    @GetMapping(params = { "id" })
    public ResponseEntity<Optional<Instrument>> findById(@RequestParam("id") String id) {
        return instrumentService.getById(request, id);
    }

    @GetMapping(params = { "name" })
    public ResponseEntity<Optional<Instrument>> findByName(@RequestParam("name") String name) {
        return this.instrumentService.getByName(request, name);
    }
    //
    @GetMapping
    public ResponseEntity<List<Instrument>> findAll() {
        String responsibleId;
        List<Instrument> instruments = new ArrayList<>();

        if (this.commonService.isAdmin(request) || this.commonService.isPI(request)) {
            responsibleId = this.commonService.getCurrrentUserId(request);
            instruments = instrumentDao.findByResponsibleIdContains(responsibleId);
            return ResponseEntity.status(HttpStatus.SC_OK).body(instruments);
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(instruments);
    }


    @GetMapping("/{parameter_name}/{uuid}")
    public ResponseEntity<List<Map<String, String>>> getParameterData(@ApiParam(value = "Air Temp")@PathVariable @Valid String parameter_name,
                                                                      @ApiParam(value = "91440f71-9c3e-5d31-befc-2729873ce581") @PathVariable String uuid ) {
        List<Map<String, String>> parameterData = new ArrayList<>();

        if (this.commonService.isAdmin(request) || this.commonService.isPI(request)) {
            parameterData = this.instrumentService.getParameterData(parameter_name, uuid);
            return ResponseEntity.status(HttpStatus.SC_OK).body(parameterData);
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(parameterData);

    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody @Valid Instrument instrument) {
        Instrument instrumentAdded;

        if(instrument == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isAdmin(request)) {
            instrumentAdded = instrumentDao.save(instrument);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(instrumentAdded.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(NOT_ALLOWED_TO_ADD_INSTRUMENT);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody Instrument instrument) {

        if(instrument == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isAdmin(request)) {
            instrumentDao.save(instrument);
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Update instrument");
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }

    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<String> delete(@PathVariable String id) {

        if(id == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isAdmin(request)) {
            instrumentDao.deleteById(id);
            return ResponseEntity.status(HttpStatus.SC_OK).body("Delete instrument");
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }

    @GetMapping(value = "infos/{id}")
    public Optional<DataInfo> getDataInformation(@PathVariable String id) {
        return dataInfoDao.findById(id);
    }
}
