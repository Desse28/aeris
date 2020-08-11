package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.flags.Flag;
import com.aeris.datavalidationrest.parameters.Parameter;

import java.util.Arrays;
import java.util.List;

public class Instrument {
    private String uuid;
    private List<String> responsibleId;
    private List<Parameter> parameters;
    private List<Parameter> auxParameters;
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

    public List<String> getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(List<String> responsibleId) {
        this.responsibleId = responsibleId;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Parameter> getAuxParameters() {
        return auxParameters;
    }

    public void setAuxParameters(List<Parameter> auxParameters) {
        this.auxParameters = auxParameters;
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
                ", responsibleId=" + responsibleId +
                ", parameters=" + parameters +
                ", auxParameters=" + auxParameters +
                ", flags=" + Arrays.toString(flags) +
                '}';
    }
}
