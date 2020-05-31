package com.m2comm.headache.DTO;

import com.m2comm.headache.contentStepView.Step10;

import java.util.ArrayList;

public class Step10SaveDTO {

    String ache_effect1;
    String ache_effect2;
    String ache_effect3;
    String ache_effect4;
    String ache_effect5;
    ArrayList<Step9Dates> ache_effect_date_val_Array;
    ArrayList<Step10EtcDTO> arrayList;

    public Step10SaveDTO(String ache_effect1, String ache_effect2, String ache_effect3, String ache_effect4,
                         String ache_effect5, ArrayList<Step9Dates> ache_effect_date_val_Array, ArrayList<Step10EtcDTO> arrayList) {
        this.ache_effect1 = ache_effect1;
        this.ache_effect2 = ache_effect2;
        this.ache_effect3 = ache_effect3;
        this.ache_effect4 = ache_effect4;
        this.ache_effect5 = ache_effect5;
        this.ache_effect_date_val_Array = ache_effect_date_val_Array;
        this.arrayList = arrayList;
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

    public void setAche_effect_date_val_Array(ArrayList<Step9Dates> ache_effect_date_val_Array) {
        this.ache_effect_date_val_Array = ache_effect_date_val_Array;
    }

    public void setArrayList(ArrayList<Step10EtcDTO> arrayList) {
        this.arrayList = arrayList;
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

    public ArrayList<Step9Dates> getAche_effect_date_val_Array() {
        return ache_effect_date_val_Array;
    }

    public ArrayList<Step10EtcDTO> getArrayList() {
        return arrayList;
    }
}
