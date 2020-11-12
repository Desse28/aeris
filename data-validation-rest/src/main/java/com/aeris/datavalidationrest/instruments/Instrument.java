package com.aeris.datavalidationrest.instruments;

import com.aeris.datavalidationrest.flags.Flag;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import com.aeris.datavalidationrest.parameters.Parameter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class Instrument {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;
    @ApiModelProperty( example = "Time series")
    private String typeOfData;
    @NotNull(message = "UUID cannot be null")
    @ApiModelProperty( example = "PDM_NEPHE")
    private String name;
    @NotNull(message = "UUID cannot be null")
    @ApiModelProperty( example = "91440f71-9c3e-5d31-befc-2729873ce581")
    private String uuid;
    @NotNull(message = "PIID cannot be null")
    @ApiModelProperty(
            example = "[" +
                            "19a46f95-56ae-432c-877c-477c7c7075b2," +
                            "e5ea86c6-fcec-436e-8218-280a883ac549," +
                            "ec943fa4-bb9a-4dd7-b949-5dceec25e6f4," +
                            "cf506cda-e449-4f4e-ae45-0199e5d7ee19" +
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
                        "{  \"code\": \"110\", \"label\": \"Episode data checked and accepted by data originator\"}," +
                        "{  \"code\": \"559\", \"label\": \"Unspecified contamination or local influence\"}," +
                        "{  \"code\": \"980\", \"label\": \"Missing due to calibration or zero/span check\"}," +
                        "{  \"code\": \"999\", \"label\": \"Missing measurement\"}," +
                    "]"
    )
    private List<Flag> flags;
    @NotNull(message = "StartDate cannot be null")
    @ApiModelProperty( example = "2019-05-17T00:00:00.0Z" )
    private Date startDate;
    @NotNull(message = "EndDate cannot be null")
    @ApiModelProperty( example = "2019-05-18T00:00:00.0Z" )
    private Date endDate;

    public Instrument() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeOfData() {
        return typeOfData;
    }

    public void setTypeOfData(String typeOfData) {
        this.typeOfData = typeOfData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "id='" + id + '\'' +
                ", typeOfData='" + typeOfData + '\'' +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", responsibleId=" + responsibleId +
                ", parameters=" + parameters +
                ", auxParameters=" + auxParameters +
                ", flags=" + flags +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
