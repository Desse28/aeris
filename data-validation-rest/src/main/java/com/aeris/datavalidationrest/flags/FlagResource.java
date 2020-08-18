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
        /*KeycloakSecurityContext scToken = (KeycloakSecurityContext) request
                .getAttribute(KeycloakSecurityContext.class.getName());
        AccessToken scAccessToken = scToken.getToken();

        Map<String, AccessToken.Access> test = scAccessToken.getResourceAccess();
        System.out.println("Test keys : ");
        System.out.println(test.keySet());
        System.out.println(request.isUserInRole("user"));

        // Récupération des rôles
       AccessToken.Access access = scAccessToken.getResourceAccess("account");
        Set<String> resourcesRoles = access.getRoles();
        access = scAccessToken.getRealmAccess();
        Set<String> realmRoles = access.getRoles();
        Set<String> roles = new HashSet<>();
        roles.addAll(resourcesRoles);
        roles.addAll(realmRoles);

        System.out.println("Test get role : ");
        for (String role: roles){
            //System.out.println(role);
        }*/
        return flagDao.findAll();
    }

    @GetMapping(value = "/{label}")
    public Flag getFlagByLabel(@PathVariable String label) {
        Flag flag = flagDao.findByLabel(label);
        return flag;
    }
}
