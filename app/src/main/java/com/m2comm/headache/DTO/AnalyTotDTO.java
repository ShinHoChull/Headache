package com.m2comm.headache.DTO;

public class AnalyTotDTO {

    private String ache_day;
    private String medicine_day;
    private String effect_day;
    private String ache_time;
    private String ache_power;
    private String ache_sign;


    public AnalyTotDTO(String ache_day, String medicine_day, String effect_day, String ache_time, String ache_power , String ache_sign ) {
        this.ache_day = ache_day;
        this.medicine_day = medicine_day;
        this.effect_day = effect_day;
        this.ache_time = ache_time;
        this.ache_power = ache_power;
        this.ache_sign = ache_sign;
    }


    public void setAche_sign(String ache_sign ){ this.ache_sign  = ache_sign;}

    public void setAche_day(String ache_day) {
        this.ache_day = ache_day;
    }

    public void setMedicine_day(String medicine_day) {
        this.medicine_day = medicine_day;
    }

    public void setEffect_day(String effect_day) {
        this.effect_day = effect_day;
    }

    public void setAche_time(String ache_time) {
        this.ache_time = ache_time;
    }

    public String getAche_sign() {return ache_sign;}

    public String getAche_day() {
        return ache_day;
    }

    public String getMedicine_day() {
        return medicine_day;
    }

    public String getEffect_day() {
        return effect_day;
    }

    public String getAche_time() {
        return ache_time;
    }

    public void setAche_power(String ache_power) {
        this.ache_power = ache_power;
    }

    public String getAche_power() {
        return ache_power;
    }
}

