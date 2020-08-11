package com.aeris.datavalidationrest.sessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SessionResource {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SessionDao sessionDao;

    @GetMapping(value = "/{id}")
    public Optional<Session> getSessionById(@PathVariable String id) {
        Optional<Session> session = sessionDao.findById(id);
        return session;
    }
}
