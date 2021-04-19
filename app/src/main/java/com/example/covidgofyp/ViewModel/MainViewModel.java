package com.example.covidgofyp.ViewModel;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.covidgofyp.Model.CovidData;
import com.example.covidgofyp.Model.CovidGlobalData;
import com.example.covidgofyp.Model.TopCountriesData;
import com.example.covidgofyp.Repositories.CovidDataRepo;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<CovidData> covidData;
    private CovidDataRepo covidDataRepo;
    private MutableLiveData<CovidGlobalData> covidGlobalData;
    private MutableLiveData<List<TopCountriesData>> chartData;
    Context context;
    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        covidDataRepo = CovidDataRepo.getInstance();
        covidData = covidDataRepo.setCovidData(context);
        covidGlobalData = covidDataRepo.setCovidGlobalData(context);
        chartData = covidDataRepo.setTopCountries(context);

    }

    public MutableLiveData<CovidData> getCovidData() {
        return covidData;
    }

    public MutableLiveData<CovidGlobalData> getCovidGlobalData() {
        return covidGlobalData;
    }

    public MutableLiveData<List<TopCountriesData>> getChartData() {
        return chartData;
    }
}
