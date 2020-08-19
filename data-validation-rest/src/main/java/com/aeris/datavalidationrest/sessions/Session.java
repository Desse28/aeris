package com.aeris.datavalidationrest.sessions;
import com.aeris.datavalidationrest.parameters.Parameter;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class Session {
    @ApiModelProperty(hidden=true)
    private String id;
    @ApiModelProperty(hidden=true)
    private String piId;
    @NotNull(message = "State cannot be null")
    @ApiModelProperty( example = "false" )
    private boolean state;
    @NotNull(message = "StartDate cannot be null")
    @ApiModelProperty( example = "2020-08-19T09:14:21.274Z" )
    private Date startDate;
    @NotNull(message = "EndDate cannot be null")
    @ApiModelProperty( example = "2020-08-19T09:14:21.274Z" )
    private Date endDate;
    @NotNull(message = "InstrumentId cannot be null")
    @ApiModelProperty( example = "5f3cebf56aa6f0740378af21" )
    private String instrumentId;
    @NotNull(message = "MainParameter cannot be null")
    @ApiModelProperty( example = "{\"name\": \"Pressure\"}")
    private Parameter mainParameter;
    @ApiModelProperty(
            example = "[" +
                    "{ \"name\": \"Air Temp\"}," +
                    "{ \"name\": \"Cell Temp\"}," +
                    "]"
    )
    private List<Parameter> parameters;
    @ApiModelProperty(
            example = "[" +
                        "{" +
                            "\"endDate\": \"2020-08-19T09:52:55.785Z\","+
                            "\"flags\": " + "["+
                                    "{\"label\": \"Valid measurement\"}," +
                                    "{\"label\": \"Episode data checked and accepted by data originator\"}," +
                                    "{\"label\": \"Missing measurement\"}," +
                                "]," +
                            "\"startDate\": \"2020-08-19T09:52:55.785Z\"" +
                        "}" +
                    "]"
    )
    private List<SessionSelection> sessionSelections;

    public Session() {
        super();
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

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
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
                "id='" + id + '\'' +
                ", piId='" + piId + '\'' +
                ", state=" + state +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", instrumentId='" + instrumentId + '\'' +
                ", mainParameter=" + mainParameter +
                ", parameters=" + parameters +
                ", sessionSelections=" + sessionSelections +
                '}';
    }
}
