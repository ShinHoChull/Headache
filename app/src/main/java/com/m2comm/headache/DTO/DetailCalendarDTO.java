package com.m2comm.headache.DTO;

import java.util.Date;

public class DetailCalendarDTO {

    private boolean isHeader;
    private String strDate;
    private String date;
    private int state;
    private String des;
    private int diary_key;

    public DetailCalendarDTO(int diary_key,boolean isHeader, String strDate, String date, int state, String des) {
        this.diary_key = diary_key;
        this.isHeader = isHeader;
        this.strDate = strDate;
        this.date = date;
        this.state = state;
        this.des = des;
    }

    public void setDiary_key(int diary_key) {
        this.diary_key = diary_key;
    }

    public int getDiary_key() {
        return diary_key;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public String getStrDate() {
        return strDate;
    }

    public String getDate() {
        return date;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }
}
