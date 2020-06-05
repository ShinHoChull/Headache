package com.m2comm.headache.DTO;

public class AnalyGraphDTO {

    private int month;
    private int ache_day;
    private int medicine_day;
    private int effect_day;
    private int ache_power;

    public AnalyGraphDTO(int month, int ache_day, int medicine_day, int effect_day, int ache_power) {
        this.month = month;
        this.ache_day = ache_day;
        this.medicine_day = medicine_day;
        this.effect_day = effect_day;
        this.ache_power = ache_power;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setAche_day(int ache_day) {
        this.ache_day = ache_day;
    }

    public void setMedicine_day(int medicine_day) {
        this.medicine_day = medicine_day;
    }

    public void setEffect_day(int effect_day) {
        this.effect_day = effect_day;
    }

    public void setAche_power(int ache_power) {
        this.ache_power = ache_power;
    }

    public int getMonth() {
        return month;
    }

    public int getAche_day() {
        return ache_day;
    }

    public int getMedicine_day() {
        return medicine_day;
    }

    public int getEffect_day() {
        return effect_day;
    }

    public int getAche_power() {
        return ache_power;
    }
}
