package com.m2comm.headache.DTO;

public class Step10Dates {
    Long date;
    String val;

    public Step10Dates(Long date, String val) {
        this.date = date;
        this.val = val;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Long getDate() {
        return date;
    }

    public String getVal() {
        return val;
    }
}
