package com.studentanger.safeheaven.Model;

public class Members {

    String name,phone,email,flat,societyCode;
    String imageUrl,owner;

    public Members() {
    }

    public Members(String name, String phone, String email, String flat, String societyCode, String imageUrl, String owner) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.flat = flat;
        this.societyCode = societyCode;
        this.imageUrl = imageUrl;
        this.owner = owner;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getSocietyCode() {
        return societyCode;
    }

    public void setSocietyCode(String societyCode) {
        this.societyCode = societyCode;
    }
}
