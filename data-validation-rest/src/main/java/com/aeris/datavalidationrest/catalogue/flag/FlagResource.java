package com.aeris.datavalidationrest.catalogue.flag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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
        /*KeycloakSecurityContext scToken = (KeycloakSecurityContext) request
                .getAttribute(KeycloakSecurityContext.class.getName());
        AccessToken scAccessToken = scToken.getToken();

        System.out.println("Test get token : ");
        System.out.println(scAccessToken);*/

        return flagDao.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Flag> getFlagById(@PathVariable String id) {
        Optional<Flag> flag = flagDao.findById(id);
        return flag;
    }
}
