package com.aeris.datavalidationrest.parameters;


import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Parameter {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;
    @ApiModelProperty( example = "Air Temp" )
    private String name;
    @ApiModelProperty( example = "rgb(148, 17, 0)" )
    private String color;
    @ApiModelProperty(hidden=true)
    private List<ParameterData> parameterData;

    public Parameter() {
        super();
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<ParameterData> getParameterData() {
        return parameterData;
    }

    public void setParameterData(List<ParameterData> parameterData) {
        this.parameterData = parameterData;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", parameterData=" + parameterData +
                '}';
    }
}
