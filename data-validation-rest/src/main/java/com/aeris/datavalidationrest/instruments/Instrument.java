package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.flags.Flag;
import com.aeris.datavalidationrest.parameters.Parameter;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Instrument {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;
    @NotNull(message = "UUID cannot be null")
    @ApiModelProperty( example = "91440f71-9c3e-5d31-befc-2729873ce581")
    private String uuid;
    @NotNull(message = "PIID cannot be null")
    @ApiModelProperty(
            example = "[" +
                            "19a46f95-56ae-432c-877c-477c7c7075b2," +
                            "e5ea86c6-fcec-436e-8218-280a883ac549" +
                    "]"
    )
    private List<String> responsibleId;
    @ApiModelProperty(
            example = "[" +
                        "{ \"name\": \"Air Temp\"}," +
                        "{ \"name\": \"Cell Temp\"}," +
                        "{ \"name\": \"Pressure\"}," +
                        "{ \"name\": \"RH\"}," +
                    "]"
    )
    private List<Parameter> parameters;
    @ApiModelProperty(
            example = "[" +
                        "{ \"name\": \"90°σspB\"}," +
                        "{ \"name\": \"90°σspR\"}" +
                    "]"
    )
    private List<Parameter> auxParameters;
    @ApiModelProperty(
            example = "[" +
                        "{ \"label\": \"Valid measurement\"}," +
                        "{ \"label\": \"Episode data checked and accepted by data originator\"}," +
                        "{ \"label\": \"Valid measurement\"}," +
                        "{ \"label\": \"Unspecified contamination or local influence\"}," +
                        "{ \"label\": \"Unspecified contamination or local influence\"}," +
                        "{ \"label\": \"Missing measurement\"}," +
                    "]"
    )
    private List<Flag> flags;

    public Instrument() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                ", piId=" + responsibleId +
                ", parameters=" + parameters +
                ", auxParameters=" + auxParameters +
                ", flags=" + flags +
                '}';
    }
}
