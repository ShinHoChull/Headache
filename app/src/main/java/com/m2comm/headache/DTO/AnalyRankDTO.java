package com.m2comm.headache.DTO;

import java.util.ArrayList;

public class AnalyRankDTO {
    String medicine_txt;
    ArrayList<AnalyMedicineTimeValDTO> analyMedicineTimeValDTOS;

    public AnalyRankDTO(String medicine_txt, ArrayList<AnalyMedicineTimeValDTO> analyMedicineTimeValDTOS) {
        this.medicine_txt = medicine_txt;
        this.analyMedicineTimeValDTOS = analyMedicineTimeValDTOS;
    }

    public void setMedicine_txt(String medicine_txt) {
        this.medicine_txt = medicine_txt;
    }

    public void setAnalyMedicineTimeValDTOS(ArrayList<AnalyMedicineTimeValDTO> analyMedicineTimeValDTOS) {
        this.analyMedicineTimeValDTOS = analyMedicineTimeValDTOS;
    }

    public String getMedicine_txt() {
        return medicine_txt;
    }

    public ArrayList<AnalyMedicineTimeValDTO> getAnalyMedicineTimeValDTOS() {
        return analyMedicineTimeValDTOS;
    }
}
