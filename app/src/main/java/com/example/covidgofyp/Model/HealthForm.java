package com.example.covidgofyp.Model;

public class HealthForm {
    public String fullname, phoneNum, nric, address, healthDescription, userId;

    public HealthForm(String fullname, String phoneNum, String nric, String address, String healthDescription, String userId) {
        this.fullname = fullname;
        this.phoneNum = phoneNum;
        this.nric = nric;
        this.address = address;
        this.healthDescription = healthDescription;
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHealthDescription() {
        return healthDescription;
    }

    public void setHealthDescription(String healthDescription) {
        this.healthDescription = healthDescription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
