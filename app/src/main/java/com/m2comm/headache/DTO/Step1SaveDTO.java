package com.m2comm.headache.DTO;

public class Step1SaveDTO {
    private Long sdate;
    private Long eDate;
    private String address;
    private double pressure;
    private double humidity;
    private double temp;
    private String weather_icon;

    public Step1SaveDTO(Long sdate, Long eDate, String address) {
        this.sdate = sdate;
        this.eDate = eDate;
        this.address = address;
    }

    public Step1SaveDTO(Long sdate, Long eDate, String address, double pressure, double humidity, double temp, String weather_icon) {
        this.sdate = sdate;
        this.eDate = eDate;
        this.address = address;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp = temp;
        this.weather_icon = weather_icon;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }



    public String getWeather_icon() {
        return weather_icon;
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
