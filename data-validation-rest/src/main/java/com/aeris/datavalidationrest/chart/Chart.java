package com.aeris.datavalidationrest.chart;

import com.aeris.datavalidationrest.parameters.Parameter;
import com.aeris.datavalidationrest.sessions.Selection;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Chart {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;
    @ApiModelProperty( example = "Graphique principal" )
    private String frName;
    @ApiModelProperty( example = "Main chart" )
    private String enName;
    @ApiModelProperty( example = "2019-05-16 22:09:18.872" )
    private Date startXaxis;//Default zoom
    @ApiModelProperty( example = "2019-05-16 22:09:18.872" )
    private Date endXaxis;//Default zoom
    private List<Parameter> parameters;
    private List<Selection> selections;

    public Chart() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Date getStartXaxis() {
        return startXaxis;
    }

    public void setStartXaxis(Date startXaxis) {
        this.startXaxis = startXaxis;
    }

    public Date getEndXaxis() {
        return endXaxis;
    }

    public void setEndXaxis(Date endXaxis) {
        this.endXaxis = endXaxis;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Selection> getSelections() {
        return selections;
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    @Override
    public String toString() {
        return "Chart{" +
                "id='" + id + '\'' +
                ", frName='" + frName + '\'' +
                ", enName='" + enName + '\'' +
                ", startXaxis=" + startXaxis +
                ", endXaxis=" + endXaxis +
                ", parameters=" + parameters +
                ", selections=" + selections +
                '}';
    }
}
