package com.aeris.datavalidationrest.parameters;

public class Parameter {
    private String id;
    private String value;

    public Parameter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
