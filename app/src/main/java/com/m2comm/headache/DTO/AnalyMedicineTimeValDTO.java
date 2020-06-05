package com.m2comm.headache.DTO;

public class AnalyMedicineTimeValDTO {
    String time_txt;
    int val;

    public AnalyMedicineTimeValDTO(String time_txt, int val) {
        this.time_txt = time_txt;
        this.val = val;
    }

    public void setTime_txt(String time_txt) {
        this.time_txt = time_txt;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getTime_txt() {
        return time_txt;
    }

    public int getVal() {
        return val;
    }
}
