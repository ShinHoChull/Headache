package com.m2comm.headache.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Step9Dates implements Serializable {

    String date;
    String val;

    public Step9Dates(String date, String val) {
        this.date = date;
        this.val = val;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDate() {
        return date;
    }

    public String getVal() {
        return val;
    }




}
