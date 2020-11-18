package com.aeris.datavalidationrest.netcdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Variables {
    private Parameter mainParameter;
    private Parameter level;
    private Parameter time;

    public Variables() {
    }

    public Parameter getMainParameter() {
        return mainParameter;
    }

    public void setMainParameter(Parameter mainParameter) {
        this.mainParameter = mainParameter;
    }

    public Parameter getLevel() {
        return level;
    }

    public void setLevel(Parameter level) {
        this.level = level;
    }

    public Parameter getTime() {
        return time;
    }

    public void setTime(Parameter time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Variables{" +
                "mainParameter=" + mainParameter +
                ", level=" + level +
                ", time=" + time +
                '}';
    }
}
