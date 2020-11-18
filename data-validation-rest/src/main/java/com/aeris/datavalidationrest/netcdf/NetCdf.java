package com.aeris.datavalidationrest.netcdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NetCdf {
    private Map<String, String> dimensions;
    private Variables variables;

    public Map<String, String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Map<String, String> dimensions) {
        this.dimensions = dimensions;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "NetCdf{" +
                "dimensions=" + dimensions +
                ", variables=" + variables +
                '}';
    }
}
