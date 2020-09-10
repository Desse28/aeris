package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.auth.LoginResource;
import com.aeris.datavalidationrest.common.CommonService;
import com.aeris.datavalidationrest.parameters.Parameter;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SessionService {
    private String currentPiid;
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    public ResponseEntity<Session> createNewSession(HttpServletRequest request, Session session) {
        Session newSession = session;

        this.setCurrentPiid(request);

        if(this.currentPiid != null) {
            session.setPiId(this.currentPiid);
            session.setSessionSelections(new ArrayList<>());

            if(!isSessionExist(session))
                newSession = sessionDao.save(session);
        }
        return createResponse(newSession);
    }

    public void setCurrentPiid(HttpServletRequest request) {
        if (this.commonService.isPI(request)) {
            this.currentPiid = this.commonService.getCurrrentUserId(request);
        }
    }

    public  ResponseEntity<Session> createResponse(Session session) {
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

    public boolean submitSession(Session session) {
        return true;
    }
}
