package com.aeris.datavalidationrest.catalogue.datainfo;

import com.aeris.datavalidationrest.catalogue.flag.Flag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data-information")
public class DataInfoResource {
    @Autowired
    private DataInfoDao dataInfoDao;

    @GetMapping
    public List<DataInfo> getAllFlags() {
        return dataInfoDao.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<DataInfo> getDataInformation(@PathVariable String id) {
        Optional<DataInfo> dataInformation = dataInfoDao.findById(id);
        return dataInformation;
    }
}