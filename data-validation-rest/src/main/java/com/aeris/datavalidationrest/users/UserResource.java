package com.aeris.datavalidationrest.users;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserResource {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDao userDao;


    public Optional<User> getUserById(@PathVariable String id) {
        Optional<User> user = userDao.findById(id);
        return user;
    }

    public User getCurrentUser() {
            KeycloakSecurityContext scToken = (KeycloakSecurityContext) request
                    .getAttribute(KeycloakSecurityContext.class.getName());
            AccessToken scAccessToken = scToken.getToken();
            //return this.conversionService.convertToDto(this.userService.findByEmail(scAccessToken.getEmail()));
        return null;
    }
}
