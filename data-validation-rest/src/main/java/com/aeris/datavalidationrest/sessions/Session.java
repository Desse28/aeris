package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.parameters.Parameter;
import org.springframework.data.annotation.Id;

import java.util.Arrays;

public class Session {
    @Id
    private String id;
    private String userId;
    private String startDate;// type Date ?
    private String endDate;
    private Parameter mainParameter;
    private Parameter[] parameters; // or define linkedPameters list and auxParameters list

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Parameter getMainParameter() {
        return mainParameter;
    }

    public void setMainParameter(Parameter mainParameter) {
        this.mainParameter = mainParameter;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", mainParameter=" + mainParameter +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
