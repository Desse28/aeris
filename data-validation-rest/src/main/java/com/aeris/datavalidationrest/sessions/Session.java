package com.aeris.datavalidationrest.sessions;
import com.aeris.datavalidationrest.chart.Chart;
import com.aeris.datavalidationrest.parameters.Parameter;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class Session {
    @ApiModelProperty(hidden=true)
    @Id
    private String id;
    @ApiModelProperty(hidden=true)
    private Date creationDate;
    @ApiModelProperty(hidden=true)
    private String piId;
    @NotNull(message = "InstrumentId cannot be null")
    @ApiModelProperty( example = "PDM_NEPHE" )
    private String instrumentName;
    @NotNull(message = "MainParameter cannot be null")
    @ApiModelProperty( example = "{\"name\": \"Pressure\"}")
    private Parameter mainParameter;
    @ApiModelProperty(
            example = "[" +
                    "{ \"name\": \"Air Temp\"}," +
                    "{ \"name\": \"Cell Temp\"}," +
                    "]"
    )

    private List<Parameter> linkedParameters;

    private List<Chart> charts;

    public Session() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public Parameter getMainParameter() {
        return mainParameter;
    }

    public void setMainParameter(Parameter mainParameter) {
        this.mainParameter = mainParameter;
    }

    public List<Parameter> getLinkedParameters() {
        return linkedParameters;
    }

    public void setLinkedParameters(List<Parameter> linkedParameters) {
        this.linkedParameters = linkedParameters;
    }

    public List<Chart> getCharts() {
        return charts;
    }

    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", creationDate=" + creationDate +
                ", piId='" + piId + '\'' +
                ", instrumentName='" + instrumentName + '\'' +
                ", mainParameter=" + mainParameter +
                ", linkedParameters=" + linkedParameters +
                ", charts=" + charts +
                '}';
    }
}
