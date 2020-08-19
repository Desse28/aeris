package com.aeris.datavalidationrest.common;

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
public class CommonService {
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String PI_ROLE = "PI";

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    public AccessToken getAccessToken(HttpServletRequest request) {
        KeycloakSecurityContext scToken = (KeycloakSecurityContext) request
                .getAttribute(KeycloakSecurityContext.class.getName());

        AccessToken scAccessToken = scToken.getToken();

        return scAccessToken;
    }

    public Set<String> getCurrentUserRoles(HttpServletRequest request) {
        AccessToken scAccessToken = getAccessToken(request);
        AccessToken.Access access = scAccessToken.getRealmAccess();
        Set<String> realmRoles = access.getRoles();
        Set<String> roles = new HashSet<>();
        roles.addAll(realmRoles);
        return roles;
    }

    public boolean isPI(HttpServletRequest request) {
        return getCurrentUserRoles(request).contains(PI_ROLE);
    }

    public boolean isAdmin(HttpServletRequest request) {
        return getCurrentUserRoles(request).contains(ADMIN_ROLE);
    }
}
