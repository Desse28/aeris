package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.common.CommonService;
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
import java.util.List;

@RestController
@RequestMapping("/instruments")
public class InstrumentResource {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private InstrumentDao instrumentDao;
    @Autowired
    private CommonService commonService;

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    @GetMapping
    public List<Instrument> findAll() {
        //By PI id
        List<Instrument> instruments = instrumentDao.findAll();
        return instruments;
    }

    @GetMapping(value = "/{uuid}")
    public Instrument findByUuid(@PathVariable String uuid) {
        Instrument instrument = instrumentDao.findByUuid(uuid);
        return instrument;
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
                    .buildAndExpand(instrumentAdded.getUuid())
                    .toUri();

            return ResponseEntity.created(location).build();
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }

    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<String> delete(@PathVariable Instrument instrument) {

        if(instrument == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isAdmin(request)) {
            instrumentDao.delete(instrument);
            return ResponseEntity.status(HttpStatus.SC_OK).body("Update");
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody Instrument instrument) {

        if(instrument == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isAdmin(request)) {
            instrumentDao.save(instrument);
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Update");
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }
}
