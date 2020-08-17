package com.aeris.datavalidationrest.sessions;

import com.aeris.datavalidationrest.flags.Flag;

import java.util.Date;
import java.util.List;

public class SessionSelection {
    private Date startDate;
    private Date endDate;
    private List<Flag> flags;

    public SessionSelection() {
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

    public List<Flag> getFlags() {
        return flags;
    }

    public void setFlags(List<Flag> flags) {
        this.flags = flags;
    }

    @Override
    public String toString() {
        return "SessionSelection{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", flags=" + flags +
                '}';
    }
}
