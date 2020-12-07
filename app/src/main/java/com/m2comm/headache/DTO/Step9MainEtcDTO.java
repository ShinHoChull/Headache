package com.m2comm.headache.DTO;

import java.io.Serializable;

/*
 * 2020.10.29 최신 변경사항
 * Parent Step9MainDTO
 * */
public class Step9MainEtcDTO implements Serializable {

    private String key;
    private String content;
    private String val;

    public Step9MainEtcDTO(String key, String content, String val) {
        this.key = key;
        this.content = content;
        this.val = val;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public String getContent() {
        return content;
    }

    public String getVal() {
        return val;
    }
}
