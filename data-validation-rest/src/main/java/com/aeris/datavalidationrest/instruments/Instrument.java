package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.parameters.Parameter;
import com.aeris.datavalidationrest.users.User;

import java.util.Arrays;

public class Instrument {
    private String uuid;
    private User responsible;
    private Parameter[] parameters;

    public Instrument() {
        super();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "uuid='" + uuid + '\'' +
                ", responsible=" + responsible +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
