package com.studentanger.safeheavenhost.Model;

public class Society {

    String name,code,area,city,state;

    public Society(String name, String code, String area, String city, String state) {
        this.name = name;
        this.code = code;
        this.area = area;
        this.city = city;
        this.state = state;
    }

    public Society() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
