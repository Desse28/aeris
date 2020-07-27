package com.aeris.datavalidationrest.catalogue.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin
@RestController
@RequestMapping("/ground-data")
public class GroundDataResource {
    @Autowired
    private GroundDataDao groundDataDao;

    @GetMapping
    public List<GroundData> getAllFlags() {
        return groundDataDao.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<GroundData> getGroundDataById(@PathVariable String id) {
        Optional<GroundData> data = groundDataDao.findById(id);
        return data;
    }
}
