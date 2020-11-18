package com.aeris.datavalidationrest.netcdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dimensions {
    private int time;
    private int level;

    public Dimensions() {
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Dimensions{" +
                "time=" + time +
                ", level=" + level +
                '}';
    }
}
