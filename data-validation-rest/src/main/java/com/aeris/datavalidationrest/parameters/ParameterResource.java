package com.aeris.datavalidationrest.parameters;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/parameters")
public class ParameterResource {
    @Autowired
    private ParameterService parameterService;

    @PostMapping(value = "/{uuid}")
    public ResponseEntity<String> importParameters(@PathVariable String uuid) {
        if(uuid == null)
            return ResponseEntity.noContent().build();
        else
            return parameterService.importParameters(uuid);
    }

    @GetMapping(value = "/{id}")
    public Optional<Parameter> findParametById(@PathVariable String id) {
        return this.parameterService.getParametById(id);
    }

    @GetMapping(value = "/{name}/{startDate}/{endDate}")
    public Parameter findByPeriod(@ApiParam(value = "Air Temp") @PathVariable String name,
                                  @ApiParam(value = "2019-05-16T22:00:00.000+00:00") @PathVariable String startDate,
                                  @ApiParam(value = "2019-05-16T22:02:01.000+00:00") @PathVariable String endDate) {
        return this.parameterService.getParameterDataByPeriod(name, startDate, endDate);
    }
}
