package com.example.covidgofyp.Model;

public class NgoForm {
    public String fullname, phoneNum, nric, address, aidDescription, userId, status, date, username;

    public NgoForm(){}

    public NgoForm(String fullname, String phoneNum, String nric, String address, String aidDescription, String userId, String status, String date, String username) {
        this.fullname = fullname;
        this.phoneNum = phoneNum;
        this.nric = nric;
        this.address = address;
        this.aidDescription = aidDescription;
        this.userId = userId;
        this.status = status;
        this.date = date;
        this.username = username;
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

    public String getAidDescription() {
        return aidDescription;
    }

    public void setAidDescription(String aidDescription) {
        this.aidDescription = aidDescription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
