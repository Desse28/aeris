package com.aeris.datavalidationrest.parameters;

import java.util.Date;

public class ParameterData {
    private Date date;
    private double value;

    public ParameterData() {
        super();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ParameterData{" +
                "date=" + date +
                ", value=" + value +
                '}';
    }
}
