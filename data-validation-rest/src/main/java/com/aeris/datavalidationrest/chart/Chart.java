package com.aeris.datavalidationrest.chart;

import com.aeris.datavalidationrest.parameters.Parameter;
import com.aeris.datavalidationrest.sessions.Selection;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Chart {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;
    private String name;
    private Double startXaxis;//Default zoom
    private Double endXaxis;//Default zoom
    private List<Parameter> parameters;
    private List<Selection> selections;

    public Chart() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStartXaxis() {
        return startXaxis;
    }

    public void setStartXaxis(Double startXaxis) {
        this.startXaxis = startXaxis;
    }

    public Double getEndXaxis() {
        return endXaxis;
    }

    public void setEndXaxis(Double endXaxis) {
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
                ", name='" + name + '\'' +
                ", startXaxis=" + startXaxis +
                ", endXaxis=" + endXaxis +
                ", parameters=" + parameters +
                ", selections=" + selections +
                '}';
    }
}
