package com.aeris.datavalidationrest.netcdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameter {
    public Parameter() {
    }
    /* private List<String> shape;
    private String type;
    private Map<String, String> attributes;
    private List<List<Float>> data;

    public Parameter() {
    }

    public List<String> getShape() {
        return shape;
    }

    public void setShape(List<String> shape) {
        this.shape = shape;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<List<Float>> getData() {
        return data;
    }

    public void setData(List<List<Float>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "shape=" + shape +
                ", type='" + type + '\'' +
                ", attributes=" + attributes +
                ", data=" + data +
                '}';
    }*/
}