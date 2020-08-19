package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.auth.LoginResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SessionService {
    @Autowired
    private RestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    public boolean submitSession(Session session) {
        return true;
    }
}
