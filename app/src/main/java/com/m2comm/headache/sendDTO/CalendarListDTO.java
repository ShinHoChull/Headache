package com.m2comm.headache.sendDTO;

public class CalendarListDTO {

    int diary_key;
    Long sdate;
    String date_txt;
    int ache_power;
    String ache_power_txt;
    String medicine_txt;

    public CalendarListDTO(int diary_key, Long sdate, String date_txt, int ache_power, String ache_power_txt, String medicine_txt) {
        this.diary_key = diary_key;
        this.sdate = sdate;
        this.date_txt = date_txt;
        this.ache_power = ache_power;
        this.ache_power_txt = ache_power_txt;
        this.medicine_txt = medicine_txt;
    }

    public void setDiary_key(int diary_key) {
        this.diary_key = diary_key;
    }

    public void setSdate(Long sdate) {
        this.sdate = sdate;
    }

    public void setDate_txt(String date_txt) {
        this.date_txt = date_txt;
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

    public int getDiary_key() {
        return diary_key;
    }

    public Long getSdate() {
        return sdate;
    }

    public String getDate_txt() {
        return date_txt;
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
