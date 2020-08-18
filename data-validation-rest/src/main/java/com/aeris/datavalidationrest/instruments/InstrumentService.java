package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.auth.LoginResource;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Service
public class InstrumentService {
    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    public AccessToken getAccessToken(HttpServletRequest request) {
        KeycloakSecurityContext scToken = (KeycloakSecurityContext) request
                .getAttribute(KeycloakSecurityContext.class.getName());

        AccessToken scAccessToken = scToken.getToken();

        return scAccessToken;
    }

    public void getCurrentUserRole(HttpServletRequest request) {
        AccessToken scAccessToken = getAccessToken(request);
        AccessToken.Access access = scAccessToken.getRealmAccess();
        Set<String> realmRoles = access.getRoles();
        Set<String> roles = new HashSet<>();
        roles.addAll(realmRoles);

        logger.info("test token : ");
        logger.info(String.valueOf(request.isUserInRole("user")));
        logger.info(String.valueOf(roles.contains("ADMIN")));

        for (String role: roles) {
            logger.info(role);
        }
    }
}
