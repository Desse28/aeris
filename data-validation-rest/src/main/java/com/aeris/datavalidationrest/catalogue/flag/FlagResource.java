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

    public void test() {
        //KeycloakSecurityContext scToken = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        //AccessToken scAccessToken = scToken.getToken();

        // Extraction des données
        //String id = scAccessToken.getId();
        //String name = scAccessToken.getPreferredUsername();
        //String email = scAccessToken.getEmail();

        // Récupération des rôles
        //AccessToken.Access access = scAccessToken.getResourceAccess("APP-SPRING");
        //Set<String> resourcesRoles = access.getRoles();
        //access = scAccessToken.getRealmAccess();
        //Set<String> realmRoles = access.getRoles();
        //Set<String> roles = new HashSet<String>();
        //roles.addAll(resourcesRoles);
        //roles.addAll(realmRoles);
    }
}
