package com.example.covidgofyp.View;

import com.example.covidgofyp.Model.Hospital;

import java.util.List;

public interface IHospitalLoadListener {
    void onHospitalLoadSuccess(List<Hospital> hospitalList);
    void onHospitalLoadFailed(String message);
}
