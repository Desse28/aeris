package com.aeris.datavalidationrest.sessions;

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

    @GetMapping
    public List<Session> findAll() {
        //By PI id
        List<Session> sessions = sessionDao.findAll();
        return sessions;
    }

    @GetMapping(value = "/{piId}")
    public Session findByPiid(@PathVariable String piId) {
        Session session = sessionDao.findByPiId(piId);
        return session;
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid Session session) {
        //By PI id's
        Session sessionAdded;

        if(session == null)
            return ResponseEntity.noContent().build();

        sessionAdded = sessionDao.save(session);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sessionAdded.getPiId())
                .toUri();

        return ResponseEntity.created(location).build();
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
