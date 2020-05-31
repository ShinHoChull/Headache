package com.m2comm.headache.DTO;

public class Step11SaveDTO {

    private Long mens_sdate;
    private Long mens_edate;

    public Step11SaveDTO(Long mens_sdate, Long mens_edate) {
        this.mens_sdate = mens_sdate;
        this.mens_edate = mens_edate;
    }

    public void setMens_sdate(Long mens_sdate) {
        this.mens_sdate = mens_sdate;
    }

    public void setMens_edate(Long mens_edate) {
        this.mens_edate = mens_edate;
    }

    public Long getMens_sdate() {
        return mens_sdate;
    }

    public Long getMens_edate() {
        return mens_edate;
    }
}
