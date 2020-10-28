package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.flags.Flag;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Selection {
    @ApiModelProperty( example = "2020-08-19T09:14:21.274Z" )
    private Date startDate;
    @ApiModelProperty( example = "2020-08-19T09:14:21.274Z" )
    private Date endDate;
    @ApiModelProperty(
            example = "["+
                        "{\"label\": \"Valid measurement\"}," +
                        "{\"label\": \"Missing measurement\"}," +
                    "]"
    )
    private List<Flag> flags;

    public Selection() {}

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

    public List<Flag> getFlags() {
        return flags;
    }

    public void setFlags(List<Flag> flags) {
        this.flags = flags;
    }

    @Override
    public String toString() {
        return "SessionSelection{" +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", flags=" + flags +
                '}';
    }
}
