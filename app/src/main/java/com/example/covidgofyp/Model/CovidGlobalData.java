package com.example.covidgofyp.Model;

public class CovidGlobalData extends CovidData {
    private String globalTotal;
    private String globalActive;
    private String globalRecovered;
    private String globalDeaths;

    public CovidGlobalData(String globalTotal, String globalActive, String globalRecovered, String globalDeaths) {
        super(globalTotal, globalActive, globalRecovered, globalDeaths);
    }

}
