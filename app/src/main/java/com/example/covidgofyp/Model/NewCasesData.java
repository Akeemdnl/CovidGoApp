package com.example.covidgofyp.Model;

public class NewCasesData {
    public String newCases, newRecovered, newDeaths;

    public NewCasesData() {
    }

    public NewCasesData(String newCases, String newRecovered, String newDeaths) {
        this.newCases = newCases;
        this.newRecovered = newRecovered;
        this.newDeaths = newDeaths;
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
}
