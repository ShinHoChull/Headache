package com.m2comm.headache.DTO;

import java.util.Date;

public class AlarmDTO {

    private Date date;
    private byte[] week;
    private boolean isPush;
    private int alarmId;

    public AlarmDTO(Date date, byte[] week, boolean isPush, int alarmId) {
        this.date = date;
        this.week = week;
        this.isPush = isPush;
        this.alarmId = alarmId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setWeek(byte[] week) {
        this.week = week;
    }

    public void setPush(boolean push) {
        isPush = push;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public Date getDate() {
        return date;
    }

    public byte[] getWeek() {
        return week;
    }

    public boolean isPush() {
        return isPush;
    }

    public int getAlarmId() {
        return alarmId;
    }
}
