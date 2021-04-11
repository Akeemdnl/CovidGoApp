package com.example.covidgofyp.Repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.covidgofyp.Model.CovidData;
import com.example.covidgofyp.Model.CovidGlobalData;
import com.example.covidgofyp.Model.TopCountriesData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CovidDataRepo {

    private static CovidDataRepo instance;
    private CovidData covidData;
    private CovidGlobalData covidGlobalData;
    private List<TopCountriesData> topCountriesData = new ArrayList<>();

    public static CovidDataRepo getInstance(){
        if(instance == null){
            instance = new CovidDataRepo();
        }
        return instance;
    }

    public MutableLiveData<CovidData> setCovidData(Context context) {
        MutableLiveData<CovidData> data = new MutableLiveData<>();
        String url ="https://disease.sh/v3/covid-19/countries/malaysia?strict=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    covidData = new CovidData(
                            response.getString("cases"),
                            response.getString("active"),
                            response.getString("recovered"),
                            response.getString("deaths"),
                            response.getString("todayCases"),
                            response.getString("todayRecovered"),
                            response.getString("todayDeaths"),
                            response.getString("critical")
                    );
                data.setValue(covidData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error Retrieving Data from local API");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
        return data;
    }

    public MutableLiveData<CovidGlobalData> setCovidGlobalData(Context context) {
        MutableLiveData<CovidGlobalData> data = new MutableLiveData<>();
        String url ="https://disease.sh/v3/covid-19/all";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    covidGlobalData = new CovidGlobalData(
                            response.getString("cases"),
                            response.getString("active"),
                            response.getString("recovered"),
                            response.getString("deaths")
                    );
                    data.setValue(covidGlobalData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error Retrieving Data from global API");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
        return data;
    }

    public MutableLiveData<List<TopCountriesData>> setTopCountries (Context context) {
        MutableLiveData<List<TopCountriesData>> data = new MutableLiveData<>();
        String url ="https://disease.sh/v3/covid-19/countries?sort=cases";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0; i < 10; i++){
                        JSONObject country = response.getJSONObject(i);
                        topCountriesData.add(new TopCountriesData(country.getString("country"),
                                                                  country.getString("cases"),
                                                                  country.getJSONObject("countryInfo").getString("flag")));
                    }
                   data.setValue(topCountriesData);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error getting request for top countries: "+error);
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
        return data;
    }
}