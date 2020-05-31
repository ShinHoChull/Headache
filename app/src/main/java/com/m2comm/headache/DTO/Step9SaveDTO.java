package com.m2comm.headache.DTO;

import java.util.ArrayList;

public class Step9SaveDTO {

    String ache_medicine1;
    String ache_medicine2;
    String ache_medicine3;
    String ache_medicine4;
    String ache_medicine5;
    String ache_medicine6;
    String ache_medicine7;
    String ache_medicine8;
    String ache_medicine9;
    String ache_medicine10;
    String ache_medicine11;
    ArrayList<Step9DTO> step9DTOS;
    int ache_medicine_effect;

    public Step9SaveDTO(String ache_medicine1, String ache_medicine2, String ache_medicine3, String ache_medicine4,
                        String ache_medicine5, String ache_medicine6, String ache_medicine7, String ache_medicine8,
                        String ache_medicine9, String ache_medicine10, String ache_medicine11, ArrayList<Step9DTO> step9DTOS,
                        int ache_medicine_effect) {
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
        this.ache_medicine11 = ache_medicine11;
        this.step9DTOS = step9DTOS;
        this.ache_medicine_effect = ache_medicine_effect;
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

    public void setAche_medicine11(String ache_medicine11) {
        this.ache_medicine11 = ache_medicine11;
    }

    public void setStep9DTOS(ArrayList<Step9DTO> step9DTOS) {
        this.step9DTOS = step9DTOS;
    }

    public void setAche_medicine_effect(int ache_medicine_effect) {
        this.ache_medicine_effect = ache_medicine_effect;
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

    public String getAche_medicine11() {
        return ache_medicine11;
    }

    public ArrayList<Step9DTO> getStep9DTOS() {
        return step9DTOS;
    }

    public int getAche_medicine_effect() {
        return ache_medicine_effect;
    }
}
