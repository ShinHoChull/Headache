package com.m2comm.headache.DTO;

public class Step10MainDTO {

    private String date;
    private String ache_effect1;
    private String ache_effect2;
    private String ache_effect3;
    private String ache_effect4;
    private String ache_effect5;

    public Step10MainDTO(String date, String ache_effect1, String ache_effect2, String ache_effect3, String ache_effect4, String ache_effect5) {
        this.date = date;
        this.ache_effect1 = ache_effect1;
        this.ache_effect2 = ache_effect2;
        this.ache_effect3 = ache_effect3;
        this.ache_effect4 = ache_effect4;
        this.ache_effect5 = ache_effect5;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAche_effect1(String ache_effect1) {
        this.ache_effect1 = ache_effect1;
    }

    public void setAche_effect2(String ache_effect2) {
        this.ache_effect2 = ache_effect2;
    }

    public void setAche_effect3(String ache_effect3) {
        this.ache_effect3 = ache_effect3;
    }

    public void setAche_effect4(String ache_effect4) {
        this.ache_effect4 = ache_effect4;
    }

    public void setAche_effect5(String ache_effect5) {
        this.ache_effect5 = ache_effect5;
    }

    public String getDate() {
        return date;
    }

    public String getAche_effect1() {
        return ache_effect1;
    }

    public String getAche_effect2() {
        return ache_effect2;
    }

    public String getAche_effect3() {
        return ache_effect3;
    }

    public String getAche_effect4() {
        return ache_effect4;
    }

    public String getAche_effect5() {
        return ache_effect5;
    }
}
