package com.aeris.datavalidationrest.parameters;

public class Parameter {
    private String id;
    private String value;
    private boolean isLinked;
    private boolean isMainParameter;

    public Parameter() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinked(boolean linked) {
        isLinked = linked;
    }

    public boolean isMainParameter() {
        return isMainParameter;
    }

    public void setMainParameter(boolean mainParameter) {
        isMainParameter = mainParameter;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", isLinked=" + isLinked +
                ", isMainParameter=" + isMainParameter +
                '}';
    }
}
