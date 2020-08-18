package com.aeris.datavalidationrest.flags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/flags")
public class FlagResource {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FlagDao flagDao;

    @GetMapping
    public List<Flag> getAllFlags() {
        return flagDao.findAll();
    }

    @GetMapping(value = "/{label}")
    public Flag getFlagByLabel(@PathVariable String label) {
        Flag flag = flagDao.findByLabel(label);
        return flag;
    }
}
