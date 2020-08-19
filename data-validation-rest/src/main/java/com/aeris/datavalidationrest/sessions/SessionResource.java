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
    private CommonService commonService;

    private static final String NOT_ALLOWED_TO_CREATE_SESSION = "You are not allowed to create a session";

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
    public ResponseEntity<String> add(@RequestBody @Valid Session session) {
        String piid;
        Session sessionAdded;

        if(session == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isPI(request)) {
            piid = this.commonService.getCurrrentUserId(request);
            session.setPiId(piid);

            sessionAdded = sessionDao.save(session);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(sessionAdded.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(NOT_ALLOWED_TO_CREATE_SESSION);
    }

    @PutMapping(value = "/update")
    public void update(@RequestBody Session session) {
        sessionDao.save(session);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Session session) {//ADMIN and PI
        sessionDao.delete(session);
    }
}
