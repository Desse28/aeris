package com.aeris.datavalidationrest.instruments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public List<Instrument> getAllInstruments() {
        List<Instrument> instruments = instrumentDao.findAll();
        return instruments;
    }

    @GetMapping(value = "/{id}")
    public Optional<Instrument> getInstrumentById(@PathVariable String id) {
        Optional<Instrument> instrument = instrumentDao.findById(id);
        return instrument;
    }
}
