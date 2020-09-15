package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.catalogue.datainfo.DataInfo;
import com.aeris.datavalidationrest.catalogue.datainfo.DataInfoDao;
import com.aeris.datavalidationrest.parameters.Parameter;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/instruments")
public class InstrumentResource {
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private DataInfoDao dataInfoDao;

    @PostMapping
    public ResponseEntity<Instrument> insert(@RequestBody @Valid Instrument instrument) {
        if(instrument == null)
            return ResponseEntity.noContent().build();
        else
            return instrumentService.insertNewInstrument(instrument);
    }

    @GetMapping
    public ResponseEntity<List<Instrument>> findAll() {
        return this.instrumentService.getAll();
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> findAllNames() {
        return instrumentService.getAllNames();
    }

    @GetMapping(params = { "id" })
    public ResponseEntity<Optional<Instrument>> findById(@RequestParam("id") String id) {
        return instrumentService.getById(id);
    }

    @GetMapping(params = { "name" })
    public ResponseEntity<Optional<Instrument>> findByName(@RequestParam("name") String name) {
        return this.instrumentService.getByName(name);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody Instrument instrument) {
        if(instrument == null)
            return ResponseEntity.noContent().build();
        else
            return this.instrumentService.updateInstrument(instrument);
    }

    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<String> delete(@PathVariable String id) {
        if(id == null)
            return ResponseEntity.noContent().build();
        else
            return this.instrumentService.deleteInstrument(id);
    }

    @PostMapping(value = "/{uuid}")
    public ResponseEntity<String> importParameters(@RequestBody @Valid String uuid) {
        if(uuid == null)
            return ResponseEntity.noContent().build();
        else
            return instrumentService.importParameters(uuid);
    }

    @GetMapping(value = "/{name}/{startDate}/{endDate}")
    public Parameter findParameterDataByPeriod(@ApiParam(value = "Air Temp") @PathVariable String name,
                                  @ApiParam(value = "2019-05-16T22:00:00.000+00:00") @PathVariable String startDate,
                                  @ApiParam(value = "2019-05-16T22:02:01.000+00:00") @PathVariable String endDate) {
        return this.instrumentService.getParameterDataByPeriod(name, startDate,  endDate);
    }

    @GetMapping(value = "infos/{id}")
    public Optional<DataInfo> getDataInformation(@PathVariable String id) {
        return dataInfoDao.findById(id);
    }
}
