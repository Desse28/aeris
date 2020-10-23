package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.common.CommonService;
import com.aeris.datavalidationrest.common.SpringDataPageable;
import com.aeris.datavalidationrest.parameters.Parameter;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.util.Optional;

@Service
public class SessionService {
    private String currentPiid;
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CommonService commonService;

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    public void setCurrentPiid() {
        if (this.commonService.isPI(request)) {
            this.currentPiid = this.commonService.getCurrrentUserId(request);
        }
    }

    public ResponseEntity<Session> createNewSession(Session session) {
        Session newSession = session;
        this.setCurrentPiid();

        if(this.currentPiid != null) {
            session.setPiId(this.currentPiid);
            session.setSessionSelections(new ArrayList<>());

            if(!isSessionExist(session))
                newSession = sessionDao.save(session);
        }
        return createSessionResponse(newSession);
    }

    public  ResponseEntity<Session> createSessionResponse(Session session) {
        URI location;
        ResponseEntity<Session> response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(null);

        if (session != null) {
            location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(session.getId())
                    .toUri();
            response = ResponseEntity.created(location).body(session);
        }
        return response;
    }

    public ResponseEntity<Optional<Session>> getById(String id) {
        Optional<Session> session = null;

        if ( this.commonService.isPI(request)) {
            session = this.sessionDao.findById(id);
            return ResponseEntity.status(HttpStatus.SC_OK).body(session);
        }

        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(session);
    }

    public boolean isSessionExist(Session session) {
        String name;
        boolean exist = false;
        Parameter mainParameter;
        List<Parameter> linkedParameters;

        if(session != null) {
            name = session.getInstrumentName();
            mainParameter = session.getMainParameter();
            linkedParameters = session.getLinkedParameters();
            exist = sessionDao.existsByInstrumentNameAndMainParameterAndLinkedParameters(name, mainParameter, linkedParameters);
        }

        return exist;
    }

    public ResponseEntity<Page<List<Session>>> getPiSessions(int pageNumber, int size) {
        Page<List<Session>> page;
        SpringDataPageable pageable;
        ResponseEntity<Page<List<Session>>> response;

        this.setCurrentPiid();

        if(this.currentPiid != null) {
            pageable = this.getPageable(pageNumber, size);
            page = sessionDao.findAllByPiId(currentPiid, pageable);
            response = ResponseEntity.status(HttpStatus.SC_OK).body(page);
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(null);
        }

        return response;
    }

    public SpringDataPageable getPageable(int page, int size) {
        SpringDataPageable pageable = new SpringDataPageable();
        List<Order> orders = new ArrayList<>();

        orders.add(new Order(Direction.DESC, "startDate"));
        pageable.setSort(Sort.by(orders));
        pageable.setPagesize(size);
        pageable.setPagenumber(page);

        return pageable;
    }

    public ResponseEntity<String> updateSession(Session session) {
        ResponseEntity<String> response;

        if(this.commonService.isPI(request)) {
            sessionDao.save(session);
            response = ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Update session (" + session.getId() + ")");
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
        }

        return response;
    }

    public ResponseEntity<String> deleteSessionById(String id) {
        ResponseEntity<String> response;

        if(id == null)
            return ResponseEntity.noContent().build();

        if (this.commonService.isPI(request)) {
            sessionDao.deleteById(id);
            response = ResponseEntity.status(HttpStatus.SC_OK).body("Delete session");
        } else {
            response = ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body("Error Message");
        }

        return response;
    }
}
