package com.m2comm.headache.DTO;

public class AnalyReallizeDTO {

    int icon;
    String key;
    String key_txt;
    String val;

    public AnalyReallizeDTO(int icon, String key, String key_txt, String val) {
        this.icon = icon;
        this.key = key;
        this.key_txt = key_txt;
        this.val = val;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setKey_txt(String key_txt) {
        this.key_txt = key_txt;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public String getKey_txt() {
        return key_txt;
    }

    public String getVal() {
        return val;
    }
}
