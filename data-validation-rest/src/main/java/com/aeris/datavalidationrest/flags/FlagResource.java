package com.aeris.datavalidationrest.flags;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
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
        KeycloakSecurityContext scToken = (KeycloakSecurityContext) request
                .getAttribute(KeycloakSecurityContext.class.getName());
        AccessToken scAccessToken = scToken.getToken();

        Map<String, AccessToken.Access> test = scAccessToken.getResourceAccess();
        System.out.println("Test keys : ");
        System.out.println(test.keySet());
        System.out.println(request.isUserInRole("user"));
        System.out.println(request.isUserInRole("user"));
        System.out.println(request.isUserInRole("USER"));
        System.out.println(request.isUserInRole("ROLE_USER"));
        System.out.println(request.isUserInRole("ROLE_user"));
        System.out.println(request.isUserInRole("ROLE_manage-account"));
        System.out.println(request.isUserInRole("manage-account"));

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
        }
        return flagDao.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Flag> getFlagById(@PathVariable String id) {
        Optional<Flag> flag = flagDao.findById(id);
        return flag;
    }
}
