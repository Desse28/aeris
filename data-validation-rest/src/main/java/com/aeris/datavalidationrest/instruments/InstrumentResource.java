package com.aeris.datavalidationrest.instruments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/parameters")
public class InstrumentResource {
    @Autowired
    private HttpServletRequest request;
}
