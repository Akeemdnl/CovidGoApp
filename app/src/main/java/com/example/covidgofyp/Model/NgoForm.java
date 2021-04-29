package com.example.covidgofyp.Model;

public class NgoForm {
    public String fullname, phoneNum, nric, address, aidDescription, userId;

    public NgoForm(String fullname, String phoneNum, String nric, String address, String aidDescription, String userId) {
        this.fullname = fullname;
        this.phoneNum = phoneNum;
        this.nric = nric;
        this.address = address;
        this.aidDescription = aidDescription;
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
}
