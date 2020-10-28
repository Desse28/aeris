package com.aeris.datavalidationrest.chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/charts")
public class ChartResource {
    @Autowired
    private ChartService chartService;
}
