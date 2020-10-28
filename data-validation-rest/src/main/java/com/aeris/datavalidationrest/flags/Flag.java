package com.aeris.datavalidationrest.flags;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Flag {
    @ApiModelProperty( example = "0000" )
    private int code;
    @ApiModelProperty( example = "Valid measurement" )
    private String label;

    public Flag() {}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
