package com.m2comm.headache.sendDTO;

import com.m2comm.headache.DTO.Step9Dates;

import java.util.ArrayList;

public class Send9PixDTO {
    int chk_num;
    int effect;
    ArrayList<Step9Dates> date_val;

    public Send9PixDTO(int chk_num, int effect, ArrayList<Step9Dates> date_val) {
        this.chk_num = chk_num;
        this.effect = effect;
        this.date_val = date_val;
    }
}
