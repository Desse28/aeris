package com.aeris.datavalidationrest.catalogue.flag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/flags")
public class FlagResource {
    @Autowired
    private FlagDao flagDao;

    @GetMapping
    public List<Flag> getAllFlags() {
        return flagDao.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Flag> getFlagById(@PathVariable String id) {
        Optional<Flag> flag = flagDao.findById(id);
        return flag;
    }
}
