package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.common.CommonService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionResource {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private CommonService commonService;

    private static final String NOT_ALLOWED_TO_CREATE_SESSION = "You are not allowed to create a session";

    @GetMapping(value = "/{piid}/{state}")
    public ResponseEntity<List<Session>> findSessionsByPiIdAndAndState(@PathVariable String piid, @PathVariable boolean state) {
        List<Session> sessions = new ArrayList<>();

        if ( this.commonService.isPI(request)) {
            sessions = sessionDao.findAllByPiIdAndAndState(piid,state);
            return ResponseEntity.status(HttpStatus.SC_OK).body(sessions);
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(sessions);
    }

    @GetMapping(value = "/in-progress")
    public ResponseEntity<List<Session>> findInprogressSession() {
        String piid;
        piid = this.commonService.getCurrrentUserId(request);
        return findSessionsByPiIdAndAndState(piid, false);
    }

    @GetMapping(value = "/done-session")
    public ResponseEntity<List<Session>> findDoneSession() {
        String piid;
        piid = this.commonService.getCurrrentUserId(request);
        return findSessionsByPiIdAndAndState(piid, true);
    }

    @GetMapping
    public ResponseEntity<List<Session>> findAll() {
        String piid;
        List<Session> sessions = new ArrayList<>();

        if ( this.commonService.isPI(request)) {
            piid = this.commonService.getCurrrentUserId(request);
            sessions = sessionDao.findByPiId(piid);
            return ResponseEntity.status(HttpStatus.SC_OK).body(sessions);
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(sessions);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid Session session) {
        String piid;
        Session newSession;

        if(session == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isPI(request)) {
            piid = this.commonService.getCurrrentUserId(request);
            session.setPiId(piid);

            newSession = sessionDao.save(session);
            setCurrentSession(newSession);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newSession.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(NOT_ALLOWED_TO_CREATE_SESSION);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody Session session) {
        String piid;

        if(session == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isPI(request)) {
            piid = this.commonService.getCurrrentUserId(request);
            session.setPiId(piid);
            sessionDao.save(session);
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Update session (" + session.getId() + ")");
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        if(id == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isPI(request)) {
            sessionDao.deleteById(id);
            return ResponseEntity.status(HttpStatus.SC_OK).body("Delete session");
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }

    @PutMapping
    public ResponseEntity<String> setCurrentSession(@RequestBody Session newSession) {
        Session currentSession;

        if( newSession == null)
            return ResponseEntity.noContent().build();

        if(this.commonService.isPI(request)) {
            currentSession = getCurrentSession();

            if(currentSession == null) {
                sessionDao.save(newSession);
            } else {
                currentSession.setCurrentSession(false);
                newSession.setCurrentSession(true);
                sessionDao.save(currentSession);
                sessionDao.save(newSession);
            }

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Set current session(" + newSession.getId() + ")");
        }
        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }

    @GetMapping("/current-session")
    public Session getCurrentSession() {
        String piid;
        Session session = null;

        if(this.commonService.isPI(request)) {
            piid = this.commonService.getCurrrentUserId(request);
            session = this.sessionDao.findByPiIdAndCurrentSession(piid, true);
        }

        return session;
    }

    @PostMapping("/submit-session")
    public ResponseEntity<String> submitSession(@RequestBody Session session) {
        if( session == null)
            return ResponseEntity.noContent().build();

        if(this.commonService.isPI(request)) {
            session.setCurrentSession(false);
            session.setState(true);
            sessionDao.save(session);
            this.sessionService.submitSession(session);
            return ResponseEntity.status(HttpStatus.SC_OK).body("Submit session");
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }
}
