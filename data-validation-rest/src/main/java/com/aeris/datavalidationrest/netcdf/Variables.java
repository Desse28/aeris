package com.aeris.datavalidationrest.netcdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Variables {
    private Parameter main;
    private Parameter level;
    private Parameter time;

    public Variables() {
    }

    public Parameter getMain() {
        return main;
    }

    public void setMain(Parameter main) {
        this.main = main;
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
                "Main=" + main +
                ", level=" + level +
                ", time=" + time +
                '}';
    }
}
