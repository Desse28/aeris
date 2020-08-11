package com.aeris.datavalidationrest.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/parameters")
public class ParameterResource {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ParamerDao paramerDao;

    @GetMapping(value = "/{id}")
    public Optional<Parameter> getParametById(@PathVariable String id) {
        Optional<Parameter> parameter = paramerDao.findById(id);
        return parameter;
    }
}
