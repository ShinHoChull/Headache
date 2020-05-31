package com.m2comm.headache.sendDTO;

public class Step4SendDTO {
    private int key;
    private String content;
    private String val;

    public Step4SendDTO(int key, String content, String val) {
        this.key = key;
        this.content = content;
        this.val = val;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public int getKey() {
        return key;
    }

    public String getContent() {
        return content;
    }

    public String getVal() {
        return val;
    }
}
