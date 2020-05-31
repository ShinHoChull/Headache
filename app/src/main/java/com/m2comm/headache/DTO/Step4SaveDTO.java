package com.m2comm.headache.DTO;

import java.util.ArrayList;

public class Step4SaveDTO {

    String ache_type1;
    String ache_type2;
    String ache_type3;
    String ache_type4;
    String ache_type5;
    ArrayList<Step4EtcDTO> arrayList;

    public Step4SaveDTO(String ache_type1, String ache_type2, String ache_type3, String ache_type4, String ache_type5, ArrayList<Step4EtcDTO> arrayList) {
        this.ache_type1 = ache_type1;
        this.ache_type2 = ache_type2;
        this.ache_type3 = ache_type3;
        this.ache_type4 = ache_type4;
        this.ache_type5 = ache_type5;
        this.arrayList = arrayList;
    }

    public void setAche_type1(String ache_type1) {
        this.ache_type1 = ache_type1;
    }

    public void setAche_type2(String ache_type2) {
        this.ache_type2 = ache_type2;
    }

    public void setAche_type3(String ache_type3) {
        this.ache_type3 = ache_type3;
    }

    public void setAche_type4(String ache_type4) {
        this.ache_type4 = ache_type4;
    }

    public void setAche_type5(String ache_type5) {
        this.ache_type5 = ache_type5;
    }

    public void setArrayList(ArrayList<Step4EtcDTO> arrayList) {
        this.arrayList = arrayList;
    }

    public String getAche_type1() {
        return ache_type1;
    }

    public String getAche_type2() {
        return ache_type2;
    }

    public String getAche_type3() {
        return ache_type3;
    }

    public String getAche_type4() {
        return ache_type4;
    }

    public String getAche_type5() {
        return ache_type5;
    }

    public ArrayList<Step4EtcDTO> getArrayList() {
        return arrayList;
    }
}
