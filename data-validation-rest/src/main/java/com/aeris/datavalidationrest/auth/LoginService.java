package com.aeris.datavalidationrest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
    @Autowired
    private Environment env;
    @Autowired
    private RestTemplate restTemplate;

    @Value( "${keycloak.realm}" )
    private String realm;
    @Value( "${keycloak.auth-server-url}" )
    private String cloakUri;
    @Value( "${spring.auth.login.clientid}" )
    private String clientid;
    @Value( "${spring.auth.login.grant_type.password}" )
    private String grant_type_password;
    @Value( "${spring.auth.login.grant_type.refresh}" )
    private String grant_type_refresh;

    private HttpHeaders headers;
    private ResponseEntity<Token> response;
    private HttpEntity<MultiValueMap<String, String>> entity;

    public Token getToken(Login login) {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String tokenUri = cloakUri + "/realms/" + realm + "/protocol/openid-connect/token";

        map.add("password", login.getPassword());
        map.add("username", login.getUsername());
        map.add("grant_type", grant_type_password);
        map.add("client_id", clientid);

        entity = new HttpEntity<>(map, headers);

        response = restTemplate.exchange( tokenUri, HttpMethod.POST, entity, Token.class);

        if (response.getBody() != null) {
            return response.getBody();
        }

        return null;
    }

    public Token refresh(String refresh_token) {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String tokenUri = cloakUri + "/realms/" + realm + "/protocol/openid-connect/token";

        map.add("client_id", clientid);
        map.add("refresh_token", refresh_token);
        map.add("grant_type", grant_type_refresh);

        entity = new HttpEntity<>(map, headers);

        response = restTemplate.exchange( tokenUri, HttpMethod.POST, entity, Token.class);

        if (response.getBody() != null) {
            return response.getBody();
        }

        return null;
    }
}
