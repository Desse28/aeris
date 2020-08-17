package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.flags.Flag;
import com.aeris.datavalidationrest.parameters.Parameter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Instrument {
    @Id
    private String id;
    @NotNull(message = "UUID cannot be null")
    private String uuid;
    @NotNull(message = "PIID cannot be null")
    private List<String> piId;
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

    public List<String> getPiId() {
        return piId;
    }

    public void setPiId(List<String> piId) {
        this.piId = piId;
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
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", piId=" + piId +
                ", parameters=" + parameters +
                ", auxParameters=" + auxParameters +
                ", flags=" + flags +
                '}';
    }
}
