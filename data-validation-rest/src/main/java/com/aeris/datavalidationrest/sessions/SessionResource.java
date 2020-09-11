package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.common.CommonService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
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

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    private static final String NOT_ALLOWED_TO_CREATE_SESSION = "You are not allowed to create a session";

    @PostMapping(value ="/create")
    public ResponseEntity<Session> create(@RequestBody @Valid Session session) {
        return sessionService.createNewSession(request, session);
    }

    @GetMapping(params = { "page", "size" })
    public ResponseEntity<Page<List<Session>>> findByPiid(@RequestParam("page") int page, @RequestParam("size") int size) {
       return sessionService.getPiSessions(request,page, size);
    }

    //
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Session>> findById(@PathVariable String id) {
        Optional<Session> session = null;

        if ( this.commonService.isPI(request)) {
            session = this.sessionDao.findById(id);
            return ResponseEntity.status(HttpStatus.SC_OK).body(session);
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(session);
    }

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

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody Session session) {
        if (this.commonService.isPI(request)) {
            sessionDao.save(session);
            logger.info("Test update session : ");
            logger.info(session.toString());
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

    @PostMapping("/submit-session")
    public ResponseEntity<String> submitSession(@RequestBody Session session) {
        if( session == null)
            return ResponseEntity.noContent().build();

        if(this.commonService.isPI(request)) {
            session.setState(true);
            sessionDao.save(session);
            this.sessionService.submitSession(session);
            return ResponseEntity.status(HttpStatus.SC_OK).body("Submit session");
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
    }
}
