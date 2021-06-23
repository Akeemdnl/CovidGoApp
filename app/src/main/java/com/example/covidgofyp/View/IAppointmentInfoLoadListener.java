package com.example.covidgofyp.View;

import com.example.covidgofyp.Model.AppointmentInformation;

public interface IAppointmentInfoLoadListener {
    void onAppointmentInfoLoadEmpty();
    void onAppointmentInfoLoadSuccess(AppointmentInformation appointmentInformation);
    void onAppointmentInfoLoadFailed(String message);
}
