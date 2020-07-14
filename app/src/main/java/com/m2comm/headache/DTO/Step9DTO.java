package com.m2comm.headache.DTO;

import java.util.ArrayList;

public class Step9DTO {

    private int default_icon;
    private int click_icon;
    private String name;
    private Boolean etc;
    private Boolean etcBt;
    private Boolean isClick;
    private ArrayList<Step9Dates> drugArray;
    private int key;
    private int effect;

    public Step9DTO(int default_icon, int click_icon, String name, Boolean etc, Boolean etcBt, Boolean isClick, ArrayList<Step9Dates> drugArray, int key, int effect) {
        this.default_icon = default_icon;
        this.click_icon = click_icon;
        this.name = name;
        this.etc = etc;
        this.etcBt = etcBt;
        this.isClick = isClick;
        this.drugArray = drugArray;
        this.key = key;
        this.effect = effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public int getEffect() {
        return effect;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public ArrayList<Step9Dates> getDrugArray() {
        return drugArray;
    }

    public void setDrugArray(ArrayList<Step9Dates> drugArray) {
        this.drugArray = drugArray;
    }

    public void setClick(Boolean click) {
        isClick = click;
    }

    public Boolean getClick() {
        return isClick;
    }

    public void setEtcBt(Boolean etcBt) {
        this.etcBt = etcBt;
    }

    public Boolean getEtcBt() {
        return etcBt;
    }

    public void setDefault_icon(int default_icon) {
        this.default_icon = default_icon;
    }

    public void setClick_icon(int click_icon) {
        this.click_icon = click_icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEtc(Boolean etc) {
        this.etc = etc;
    }

    public int getDefault_icon() {
        return default_icon;
    }

    public int getClick_icon() {
        return click_icon;
    }

    public String getName() {
        return name;
    }

    public Boolean getEtc() {
        return etc;
    }
}
