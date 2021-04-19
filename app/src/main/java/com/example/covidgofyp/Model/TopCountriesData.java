package com.example.covidgofyp.Model;

import androidx.annotation.NonNull;

public class TopCountriesData {
    private String country;
    private String cases;
    private String deaths;
    private String flagUrl;

    public TopCountriesData(String country, String cases, String deaths, String flagUrl) {
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.flagUrl = flagUrl;
    }

    public String getCountry() {
        return country;
    }

    public String getCases() {
        return cases;
    }

    public String getDeaths() {return deaths;}

    public String getFlagUrl() {
        return flagUrl;
    }

    @NonNull
    @Override
    public String toString() {
        String testString = "\ncountry: " + this.getCountry() + "\n" +
                        "cases: " + this.getCases() + "\n" +
                        "flag:" + this.getFlagUrl() + "\n";
        return testString;
    }
}
