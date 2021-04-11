package com.example.covidgofyp.Model;

public class CovidData {
    private String totalCases;
    private String activeCases;
    private String totalRecovered;
    private String totalDeaths;
    private String newCases;
    private String newRecovered;
    private String newDeaths;
    private String critical;

    public CovidData(String totalCases, String activeCases, String totalRecovered, String totalDeaths, String newCases, String newRecovered, String newDeaths, String critical) {
        this.totalCases = totalCases;
        this.activeCases = activeCases;
        this.totalRecovered = totalRecovered;
        this.totalDeaths = totalDeaths;
        this.newCases = newCases;
        this.newRecovered = newRecovered;
        this.newDeaths = newDeaths;
        this.critical = critical;
    }
    //Constructor for Global data
    public CovidData(String totalCases, String activeCases, String totalRecovered, String totalDeaths){
        this.totalCases = totalCases;
        this.activeCases = activeCases;
        this.totalRecovered = totalRecovered;
        this.totalDeaths = totalDeaths;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        this.totalCases = totalCases;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(String activeCases) {
        this.activeCases = activeCases;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }

    public String getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(String newRecovered) {
        this.newRecovered = newRecovered;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }
}
