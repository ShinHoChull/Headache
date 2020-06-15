package com.m2comm.headache.DTO;

import java.util.Date;

public class AlarmDTO {

    private String time;
    private boolean isPush;
    private int alarmId;


    public AlarmDTO(String time, boolean isPush, int alarmId) {
        this.time = time;
        this.isPush = isPush;
        this.alarmId = alarmId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPush(boolean push) {
        isPush = push;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getTime() {
        return time;
    }

    public boolean isPush() {
        return isPush;
    }

    public int getAlarmId() {
        return alarmId;
    }
}
