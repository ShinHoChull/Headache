package com.m2comm.headache.DTO;

public class MensDTO {

    private int num;
    private Long sDate;
    private Long eDate;

    public MensDTO(int num, Long sDate, Long eDate) {
        this.num = num;
        this.sDate = sDate;
        this.eDate = eDate;
    }

    public int getNum() {
        return num;
    }

    public Long getsDate() {
        return sDate;
    }

    public Long geteDate() {
        return eDate;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setsDate(Long sDate) {
        this.sDate = sDate;
    }

    public void seteDate(Long eDate) {
        this.eDate = eDate;
    }
}
