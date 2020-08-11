package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.flags.Flag;
import com.aeris.datavalidationrest.parameters.Parameter;
import com.aeris.datavalidationrest.users.User;

import java.util.Arrays;

public class Instrument {
    private String uuid;//UUID de la fiche dans le catalogue AERIS
    private String responsibleId;
    private Parameter[] parameters;
    private Flag[] flags;

    public Instrument() {
        super();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(String responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public Flag[] getFlags() {
        return flags;
    }

    public void setFlags(Flag[] flags) {
        this.flags = flags;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "uuid='" + uuid + '\'' +
                ", responsibleId='" + responsibleId + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                ", flags=" + Arrays.toString(flags) +
                '}';
    }
}
