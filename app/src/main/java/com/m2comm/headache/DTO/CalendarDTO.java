package com.m2comm.headache.DTO;

public class CalendarDTO {

    private int diary_sid;
    private int ache_power;
    private int chk_num;
    private int mens;
    private int medicine;
    private int img_type;
    private int effect;
    private String date; //yyyy-mm-dd
    private String ache_power_txt;
    private String medicine_txt;
    private String medicine_effect_txt;

    public CalendarDTO(int diary_sid, int ache_power, int chk_num, int mens, int medicine, int img_type, int effect, String date) {
        this.diary_sid = diary_sid;
        this.ache_power = ache_power;
        this.chk_num = chk_num;
        this.mens = mens;
        this.medicine = medicine;
        this.img_type = img_type;
        this.effect = effect;
        this.date = date;
    }

    public CalendarDTO(int diary_sid, int ache_power, int chk_num, int mens, int medicine, int img_type, int effect, String date, String ache_power_txt, String medicine_txt, String medicine_effect_txt) {
        this.diary_sid = diary_sid;
        this.ache_power = ache_power;
        this.chk_num = chk_num;
        this.mens = mens;
        this.medicine = medicine;
        this.img_type = img_type;
        this.effect = effect;
        this.date = date;
        this.ache_power_txt = ache_power_txt;
        this.medicine_txt = medicine_txt;
        this.medicine_effect_txt = medicine_effect_txt;
    }

    public void setDiary_sid(int diary_sid) {
        this.diary_sid = diary_sid;
    }

    public void setAche_power_txt(String ache_power_txt) {
        this.ache_power_txt = ache_power_txt;
    }

    public void setMedicine_txt(String medicine_txt) {
        this.medicine_txt = medicine_txt;
    }

    public void setMedicine_effect_txt(String medicine_effect_txt) {
        this.medicine_effect_txt = medicine_effect_txt;
    }

    public String getAche_power_txt() {
        return ache_power_txt;
    }

    public String getMedicine_txt() {
        return medicine_txt;
    }

    public String getMedicine_effect_txt() {
        return medicine_effect_txt;
    }

    public int getDiary_sid() {
        return diary_sid;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public int getEffect() {
        return effect;
    }

    public void setAche_power(int ache_power) {
        this.ache_power = ache_power;
    }

    public void setChk_num(int chk_num) {
        this.chk_num = chk_num;
    }

    public void setMens(int mens) {
        this.mens = mens;
    }

    public void setMedicine(int medicine) {
        this.medicine = medicine;
    }

    public void setImg_type(int img_type) {
        this.img_type = img_type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAche_power() {
        return ache_power;
    }

    public int getChk_num() {
        return chk_num;
    }

    public int getMens() {
        return mens;
    }

    public int getMedicine() {
        return medicine;
    }

    public int getImg_type() {
        return img_type;
    }

    public String getDate() {
        return date;
    }
}
