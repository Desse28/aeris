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
    public List<Session> findAll() {
        List<Session> sessions = sessionDao.findAll();
        return sessions;
    }

    @GetMapping(value = "/{piId}")
    public Session findByPiid(@PathVariable String piId) {
        Session session = sessionDao.findByPiId(piId);
        return session;
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody @Valid Session session) {
        Session sessionAdded;

        if(session == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isPI(request)) {
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

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Session session) {//ADMIN and PI
        sessionDao.delete(session);
    }

    @PutMapping(value = "/update")
    public void update(@RequestBody Session session) {
        sessionDao.save(session);
    }
}
