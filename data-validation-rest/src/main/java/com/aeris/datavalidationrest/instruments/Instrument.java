package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.flags.Flag;
import com.aeris.datavalidationrest.parameters.Parameter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Instrument {
    @NotNull(message = "UUID cannot be null")
    private String uuid;
    @NotNull(message = "Responsibles Id's cannot be null")
    private List<String> responsiblesId;
    private List<Parameter> parameters;
    private List<Parameter> auxParameters;
    private List<Flag> flags;

    public Instrument() {
        super();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getResponsiblesId() {
        return responsiblesId;
    }

    public void setResponsiblesId(List<String> responsiblesId) {
        this.responsiblesId = responsiblesId;
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

    public List<Flag> getFlags() {
        return flags;
    }

    public void setFlags(List<Flag> flags) {
        this.flags = flags;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "uuid='" + uuid + '\'' +
                ", responsiblesId=" + responsiblesId +
                ", parameters=" + parameters +
                ", auxParameters=" + auxParameters +
                ", flags=" + flags +
                '}';
    }
}
