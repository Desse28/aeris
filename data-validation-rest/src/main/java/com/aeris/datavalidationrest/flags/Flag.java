package com.aeris.datavalidationrest.flags;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Flag {
    @Id
    private String id;
    private String code;
    private String label;

    public Flag() {}

    public Flag(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
