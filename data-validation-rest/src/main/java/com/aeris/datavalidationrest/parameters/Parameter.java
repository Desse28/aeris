package com.aeris.datavalidationrest.parameters;

import com.opencsv.bean.CsvBindByPosition;

public class Parameter {
    //@CsvBindByPosition(position = 0)
    private String name;

    public Parameter() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                '}';
    }
}
