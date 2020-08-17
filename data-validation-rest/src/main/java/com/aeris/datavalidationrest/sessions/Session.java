package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.instruments.Instrument;
import com.aeris.datavalidationrest.parameters.Parameter;

import java.util.Date;
import java.util.List;

public class Session {
    private String piId;
    private boolean state;
    private Date startDate;
    private Date endDate;
    private Instrument instrument;
    private Parameter mainParameter;
    private List<Parameter> parameters;
    private List<SessionSelection> sessionSelections;

    public Session() {
        super();
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
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

    public List<SessionSelection> getSessionSelections() {
        return sessionSelections;
    }

    public void setSessionSelections(List<SessionSelection> sessionSelections) {
        this.sessionSelections = sessionSelections;
    }

    @Override
    public String toString() {
        return "Session{" +
                "piId='" + piId + '\'' +
                ", state=" + state +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", instrument=" + instrument +
                ", mainParameter=" + mainParameter +
                ", parameters=" + parameters +
                ", sessionSelections=" + sessionSelections +
                '}';
    }
}
