package com.example.covidgofyp.ViewModel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.covidgofyp.Model.CovidData;
import com.example.covidgofyp.Model.CovidGlobalData;
import com.example.covidgofyp.Model.TopCountriesData;
import com.example.covidgofyp.Repositories.CovidDataRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MulticastSocket;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<CovidData> covidData;
    private CovidDataRepo covidDataRepo;
    private MutableLiveData<CovidGlobalData> covidGlobalData;
    private  MutableLiveData<List<TopCountriesData>> chartData;
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
