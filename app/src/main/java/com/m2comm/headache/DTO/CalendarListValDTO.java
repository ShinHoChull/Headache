package com.m2comm.headache.DTO;

public class CalendarListValDTO {

    int diary_sid;
    int ache_power;
    String ache_power_txt;
    String medicine_txt;

    public CalendarListValDTO(int diary_sid, int ache_power, String ache_power_txt, String medicine_txt) {
        this.diary_sid = diary_sid;
        this.ache_power = ache_power;
        this.ache_power_txt = ache_power_txt;
        this.medicine_txt = medicine_txt;
    }

    public void setDiary_sid(int diary_sid) {
        this.diary_sid = diary_sid;
    }

    public void setAche_power(int ache_power) {
        this.ache_power = ache_power;
    }

    public void setAche_power_txt(String ache_power_txt) {
        this.ache_power_txt = ache_power_txt;
    }

    public void setMedicine_txt(String medicine_txt) {
        this.medicine_txt = medicine_txt;
    }

    public int getDiary_sid() {
        return diary_sid;
    }

    public int getAche_power() {
        return ache_power;
    }

    public String getAche_power_txt() {
        return ache_power_txt;
    }

    public String getMedicine_txt() {
        return medicine_txt;
    }
}
