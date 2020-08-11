package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.instruments.Instrument;
import com.aeris.datavalidationrest.parameters.Parameter;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Session {
    @Id
    private String id;
    private boolean state;
    private String userId;
    private Date startDate;
    private Date endDate;
    private Instrument instrument;
    private Parameter mainParameter;
    private List<Parameter> parameters;

    public Session() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Parameter getMainParameter() {
        return mainParameter;
    }

    public void setMainParameter(Parameter mainParameter) {
        this.mainParameter = mainParameter;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", userId='" + userId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", instrument=" + instrument +
                ", mainParameter=" + mainParameter +
                ", parameters=" + parameters +
                '}';
    }
}
