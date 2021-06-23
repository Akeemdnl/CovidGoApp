package com.example.covidgofyp.Model;

public class TimelineData {
    String date, cases;

    public TimelineData(String date, String cases) {
        this.date = date;
        this.cases = cases;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }
}
