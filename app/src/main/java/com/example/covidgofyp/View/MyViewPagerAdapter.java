package com.example.covidgofyp.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    public MyViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return AAppointmentStep1.getInstance();
            case 1:
                return AAppointmentStep2.getInstance();
            case 2:
                return AAppointmentStep3.getInstance();
            case 3:
                return AAppointmentStep4.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
