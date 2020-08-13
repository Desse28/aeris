package com.aeris.datavalidationrest.instruments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instruments")
public class InstrumentResource {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private InstrumentDao instrumentDao;

    @GetMapping
    public List<Instrument> getInstruments() {
        List<Instrument> instruments = instrumentDao.findAll();
        return instruments;
    }

    @GetMapping(value = "/{id}")
    public Optional<Instrument> getInstrumentById(@PathVariable String id) {
        Optional<Instrument> instrument = instrumentDao.findById(id);
        return instrument;
    }

    @PostMapping
    public ResponseEntity<Void> addInstrument(@RequestBody @Valid Instrument instrument) {//PI
            Instrument instrumentAdded;

            if(instrument == null)
                return ResponseEntity.noContent().build();

            instrumentAdded = instrumentDao.save(instrument);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(instrumentAdded.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
    }

    //public void setInstrument() {
    //}

    //public void deleteInstrument() {//ADMIN
    //}
}
