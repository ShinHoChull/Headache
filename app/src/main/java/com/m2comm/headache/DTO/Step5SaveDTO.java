package com.m2comm.headache.DTO;

import java.util.ArrayList;

public class Step5SaveDTO {

    private String ache_realize_yn;
    private int ache_realize_hour;
    private int ache_realize_minute;
    private String ache_realize1;
    private String ache_realize2;
    private String ache_realize3;
    private String ache_realize4;
    private String ache_realize5;
    private String ache_realize6;
    private String ache_realize7;
    private String ache_realize8;
    private ArrayList<Step5EtcDTO> arrayList;

    public Step5SaveDTO(String ache_realize_yn, int ache_realize_hour, int ache_realize_minute, String ache_realize1, String ache_realize2, String ache_realize3, String ache_realize4,
                        String ache_realize5, String ache_realize6, String ache_realize7, String ache_realize8,
                        ArrayList<Step5EtcDTO> arrayList) {
        this.ache_realize_yn = ache_realize_yn;
        this.ache_realize_hour = ache_realize_hour;
        this.ache_realize_minute = ache_realize_minute;
        this.ache_realize1 = ache_realize1;
        this.ache_realize2 = ache_realize2;
        this.ache_realize3 = ache_realize3;
        this.ache_realize4 = ache_realize4;
        this.ache_realize5 = ache_realize5;
        this.ache_realize6 = ache_realize6;
        this.ache_realize7 = ache_realize7;
        this.ache_realize8 = ache_realize8;
        this.arrayList = arrayList;
    }

    public void setAche_realize_yn(String ache_realize_yn) {
        this.ache_realize_yn = ache_realize_yn;
    }

    public void setAche_realize_hour(int ache_realize_hour) {
        this.ache_realize_hour = ache_realize_hour;
    }

    public void setAche_realize_minute(int ache_realize_minute) {
        this.ache_realize_minute = ache_realize_minute;
    }

    public void setAche_realize1(String ache_realize1) {
        this.ache_realize1 = ache_realize1;
    }

    public void setAche_realize2(String ache_realize2) {
        this.ache_realize2 = ache_realize2;
    }

    public void setAche_realize3(String ache_realize3) {
        this.ache_realize3 = ache_realize3;
    }

    public void setAche_realize4(String ache_realize4) {
        this.ache_realize4 = ache_realize4;
    }

    public void setAche_realize5(String ache_realize5) {
        this.ache_realize5 = ache_realize5;
    }

    public void setAche_realize6(String ache_realize6) {
        this.ache_realize6 = ache_realize6;
    }

    public void setAche_realize7(String ache_realize7) {
        this.ache_realize7 = ache_realize7;
    }

    public void setAche_realize8(String ache_realize8) {
        this.ache_realize8 = ache_realize8;
    }

    public void setArrayList(ArrayList<Step5EtcDTO> arrayList) {
        this.arrayList = arrayList;
    }

    public String getAche_realize_yn() {
        return ache_realize_yn;
    }

    public int getAche_realize_hour() {
        return ache_realize_hour;
    }

    public int getAche_realize_minute() {
        return ache_realize_minute;
    }

    public String getAche_realize1() {
        return ache_realize1;
    }

    public String getAche_realize2() {
        return ache_realize2;
    }

    public String getAche_realize3() {
        return ache_realize3;
    }

    public String getAche_realize4() {
        return ache_realize4;
    }

    public String getAche_realize5() {
        return ache_realize5;
    }

    public String getAche_realize6() {
        return ache_realize6;
    }

    public String getAche_realize7() {
        return ache_realize7;
    }

    public String getAche_realize8() {
        return ache_realize8;
    }

    public ArrayList<Step5EtcDTO> getArrayList() {
        return arrayList;
    }
}
