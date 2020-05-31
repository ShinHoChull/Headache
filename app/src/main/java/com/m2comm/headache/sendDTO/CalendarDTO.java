package com.m2comm.headache.sendDTO;

public class CalendarDTO {

    private int ache_power;
    private int chk_num;
    private int mens;
    private int medicine;
    private int img_type;
    private int effect;
    private String date; //yyyy-mm-dd

    public CalendarDTO(int ache_power, int chk_num, int mens, int medicine, int img_type, String date , int effect) {
        this.ache_power = ache_power;
        this.chk_num = chk_num;
        this.mens = mens;
        this.medicine = medicine;
        this.img_type = img_type;
        this.date = date;
        this.effect = effect;
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
