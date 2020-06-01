package com.m2comm.headache.DTO;

import java.util.ArrayList;

public class CalendarListDTO {

    String date_txt;
    ArrayList<CalendarListValDTO> date_val;

    public CalendarListDTO(String date_txt, ArrayList<CalendarListValDTO> date_val) {
        this.date_txt = date_txt;
        this.date_val = date_val;
    }

    public void setDate_txt(String date_txt) {
        this.date_txt = date_txt;
    }

    public void setDate_val(ArrayList<CalendarListValDTO> date_val) {
        this.date_val = date_val;
    }

    public String getDate_txt() {
        return date_txt;
    }

    public ArrayList<CalendarListValDTO> getDate_val() {
        return date_val;
    }
}
