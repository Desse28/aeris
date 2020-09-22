package com.aeris.datavalidationrest.sessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/sessions")
public class SessionResource {
    @Autowired
    private SessionService sessionService;


    @PostMapping(value ="/create")
    public ResponseEntity<Session> create(@RequestBody @Valid Session session) {
        return sessionService.createNewSession(session);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return this.sessionService.deleteSessionById(id);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody Session session) {
        return this.sessionService.updateSession(session);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Session>> findById(@PathVariable String id) {
        return this.sessionService.getById(id);
    }

    @GetMapping(params = { "page", "size" })
    public ResponseEntity<Page<List<Session>>> findByPiid(@RequestParam("page") int page, @RequestParam("size") int size) {
       return sessionService.getPiSessions(page, size);
    }

    @PostMapping("/submit-session")
    public ResponseEntity<String> submitSession(@RequestBody Session session) {
        return this.sessionService.submitSession(session);
    }
}
