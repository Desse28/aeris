package com.aeris.datavalidationrest.netcdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NetCdf {
    private Dimensions dimensions;
    private Variables variables;

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
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
