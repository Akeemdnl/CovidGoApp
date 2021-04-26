package com.example.covidgofyp.Model;

public class NewsData {
    private String status;
    private int totalResults;
    private Articles[] articles;

    public NewsData(String status, int totalResults, Articles[] articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public Articles[] getArticles() {
        return articles;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setArticles(Articles[] articles) {
        this.articles = articles;
    }
}
