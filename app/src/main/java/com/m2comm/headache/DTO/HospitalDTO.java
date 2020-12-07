package com.m2comm.headache.DTO;

public class HospitalDTO {

    int sid;
    String name;
    String addr;

    public HospitalDTO( int sid, String name, String addr ) {
        this.sid = sid;
        this.name = name;
        this.addr = addr;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }
}
