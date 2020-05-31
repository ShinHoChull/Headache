package com.m2comm.headache.DTO;

public class Step1SaveDTO {
    private Long sdate;
    private Long eDate;
    private String address;

    public Step1SaveDTO(Long sdate, Long eDate, String address) {
        this.sdate = sdate;
        this.eDate = eDate;
        this.address = address;
    }

    public void setSdate(Long sdate) {
        this.sdate = sdate;
    }

    public void seteDate(Long eDate) {
        this.eDate = eDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSdate() {
        return sdate;
    }

    public Long geteDate() {
        return eDate;
    }

    public String getAddress() {
        return address;
    }
}
