package com.m2comm.headache.DTO;

import java.io.Serializable;
import java.util.ArrayList;
/*
* 2020.10.29 최신 변경사항
*
* */
public class Step9MainDTO implements Serializable {

    private String date;
    private String effect;
    private String ache_medicine1;
    private String ache_medicine2;
    private String ache_medicine3;
    private String ache_medicine4;
    private String ache_medicine5;
    private String ache_medicine6;
    private String ache_medicine7;
    private String ache_medicine8;
    private String ache_medicine9;
    private String ache_medicine10;
    private ArrayList<Step9MainEtcDTO> ache_medicine_etc;


    public Step9MainDTO(String date, String effect, String ache_medicine1, String ache_medicine2, String ache_medicine3, String ache_medicine4, String ache_medicine5, String ache_medicine6,
                        String ache_medicine7, String ache_medicine8, String ache_medicine9, String ache_medicine10, ArrayList<Step9MainEtcDTO> ache_medicine_etc) {
        this.date = date;
        this.effect = effect;
        this.ache_medicine1 = ache_medicine1;
        this.ache_medicine2 = ache_medicine2;
        this.ache_medicine3 = ache_medicine3;
        this.ache_medicine4 = ache_medicine4;
        this.ache_medicine5 = ache_medicine5;
        this.ache_medicine6 = ache_medicine6;
        this.ache_medicine7 = ache_medicine7;
        this.ache_medicine8 = ache_medicine8;
        this.ache_medicine9 = ache_medicine9;
        this.ache_medicine10 = ache_medicine10;
        this.ache_medicine_etc = ache_medicine_etc;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setAche_medicine1(String ache_medicine1) {
        this.ache_medicine1 = ache_medicine1;
    }

    public void setAche_medicine2(String ache_medicine2) {
        this.ache_medicine2 = ache_medicine2;
    }

    public void setAche_medicine3(String ache_medicine3) {
        this.ache_medicine3 = ache_medicine3;
    }

    public void setAche_medicine4(String ache_medicine4) {
        this.ache_medicine4 = ache_medicine4;
    }

    public void setAche_medicine5(String ache_medicine5) {
        this.ache_medicine5 = ache_medicine5;
    }

    public void setAche_medicine6(String ache_medicine6) {
        this.ache_medicine6 = ache_medicine6;
    }

    public void setAche_medicine7(String ache_medicine7) {
        this.ache_medicine7 = ache_medicine7;
    }

    public void setAche_medicine8(String ache_medicine8) {
        this.ache_medicine8 = ache_medicine8;
    }

    public void setAche_medicine9(String ache_medicine9) {
        this.ache_medicine9 = ache_medicine9;
    }

    public void setAche_medicine10(String ache_medicine10) {
        this.ache_medicine10 = ache_medicine10;
    }

    public void setAche_medicine_etc(ArrayList<Step9MainEtcDTO> ache_medicine_etc) {
        this.ache_medicine_etc = ache_medicine_etc;
    }

    public String getDate() {
        return date;
    }

    public String getEffect() {
        return effect;
    }

    public String getAche_medicine1() {
        return ache_medicine1;
    }

    public String getAche_medicine2() {
        return ache_medicine2;
    }

    public String getAche_medicine3() {
        return ache_medicine3;
    }

    public String getAche_medicine4() {
        return ache_medicine4;
    }

    public String getAche_medicine5() {
        return ache_medicine5;
    }

    public String getAche_medicine6() {
        return ache_medicine6;
    }

    public String getAche_medicine7() {
        return ache_medicine7;
    }

    public String getAche_medicine8() {
        return ache_medicine8;
    }

    public String getAche_medicine9() {
        return ache_medicine9;
    }

    public String getAche_medicine10() {
        return ache_medicine10;
    }

    public ArrayList<Step9MainEtcDTO> getAche_medicine_etc() {
        return ache_medicine_etc;
    }
}
