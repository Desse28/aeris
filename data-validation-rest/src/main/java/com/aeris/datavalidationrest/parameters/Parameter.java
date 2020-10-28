package com.aeris.datavalidationrest.parameters;


import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Parameter {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;
    private String name;
    private String color;
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
                ", parameterData=" + parameterData +
                '}';
    }
}
