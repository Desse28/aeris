package com.aeris.datavalidationrest.common;

import com.aeris.datavalidationrest.auth.LoginResource;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public String getCurrrentUserId(HttpServletRequest request) {
        AccessToken scAccessToken = getAccessToken(request);
        String userId = scAccessToken.getSubject();
        return userId;
    }

    public Date strToDate(String str, String dateFormat) throws ParseException {
        DateFormat format = new SimpleDateFormat(dateFormat);
        Date date = format.parse(str);
        return date;
    }
}
