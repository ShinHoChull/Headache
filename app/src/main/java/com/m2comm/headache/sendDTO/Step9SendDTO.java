package com.m2comm.headache.sendDTO;

import com.m2comm.headache.DTO.Step9Dates;

import java.util.ArrayList;

public class Step9SendDTO {
    int key;
    String content;
    int check_num;
    ArrayList<Step9Dates> date_val;

    public Step9SendDTO(int key, String content, int check_num, ArrayList<Step9Dates> date_val) {
        this.key = key;
        this.content = content;
        this.check_num = check_num;
        this.date_val = date_val;
    }
}
