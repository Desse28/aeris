package com.aeris.datavalidationrest.flags;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Flag {
    private String code;
    private String label;

    public Flag() {}

    public Flag(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "code='" + code + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
