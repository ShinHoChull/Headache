package com.m2comm.headache.DTO;

public class Step4EtcDTO {

    private int default_icon;
    private int click_icon;
    private String content;
    private Boolean etc;
    private Boolean etcBt;
    private Boolean isClick;
    private int key;
    private String val;

    public Step4EtcDTO(int default_icon, int click_icon, String content, Boolean etc, Boolean etcBt, Boolean isClick, int key, String val) {
        this.default_icon = default_icon;
        this.click_icon = click_icon;
        this.content = content;
        this.etc = etc;
        this.etcBt = etcBt;
        this.isClick = isClick;
        this.key = key;
        this.val = val;
    }

    public void setDefault_icon(int default_icon) {
        this.default_icon = default_icon;
    }

    public void setClick_icon(int click_icon) {
        this.click_icon = click_icon;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEtc(Boolean etc) {
        this.etc = etc;
    }

    public void setEtcBt(Boolean etcBt) {
        this.etcBt = etcBt;
    }

    public void setClick(Boolean click) {
        isClick = click;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public int getDefault_icon() {
        return default_icon;
    }

    public int getClick_icon() {
        return click_icon;
    }

    public String getContent() {
        return content;
    }

    public Boolean getEtc() {
        return etc;
    }

    public Boolean getEtcBt() {
        return etcBt;
    }

    public Boolean getClick() {
        return isClick;
    }

    public int getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }
}
