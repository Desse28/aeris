package com.aeris.datavalidationrest.sessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sessions")
public class SessionResource {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SessionDao sessionDao;

    //public void createSession() {
    //}

    @GetMapping(value = "/{id}")
    public Optional<Session> getSessionById(@PathVariable String id) {
        Optional<Session> session = sessionDao.findById(id);
        return session;
    }

    @GetMapping
    public List<Session> getSessions() {
        List<Session> sessions = sessionDao.findAll();
        return sessions;
    }

    //public void setInstrument(@PathVariable Instrument instrument) {//not validate
    //}

    //public void deleteInstrument(@PathVariable Instrument instrument) {//ADMIN (not validate or save in session)
    //}
}
