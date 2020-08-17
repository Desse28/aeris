package com.aeris.datavalidationrest.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginResource {
    @Autowired
    private LoginService loginService;
    @Autowired
    private HttpServletRequest request;

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    @PostMapping
    public ResponseEntity<Token> authenticate(@RequestBody @Valid Login login) {
        Token token;

        if(login == null)
            return ResponseEntity.noContent().build();

        token = this.loginService.getToken(login);

        if(token == null)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping(value = "/{refresh_token}")
    public ResponseEntity<Token> refreshAndGetAuthenticationToken(@PathVariable String refresh_token) {
        Token token;

        logger.info("Test refresh token : ");
        logger.info(refresh_token);

        if(refresh_token == null)
            return ResponseEntity.noContent().build();

        token = this.loginService.refresh(refresh_token);

        if(token == null)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
