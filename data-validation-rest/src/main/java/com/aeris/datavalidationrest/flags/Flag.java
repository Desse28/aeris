package com.aeris.datavalidationrest.flags;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Flag {
    private String label;

    public Flag() {}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "label='" + label + '\'' +
                '}';
    }
}
