package com.example.SunriseSunset.dto;

/**Represents the response model for sunrise and sunset data from the external API.*/
public class SunriseSunsetModel {

    /** The results containing sunrise and sunset times. */
    private Results results;

    /** The status of the API response. */
    private String status;

    /**Gets the results containing sunrise and sunset times.*/
    public Results getResults() {
        return results;
    }

    /**Sets the results containing sunrise and sunset times.*/
    public void setResults(Results results) {
        this.results = results;
    }

    /**Gets the status of the API response.*/
    public String getStatus() {
        return status;
    }

    /**Sets the status of the API response.*/
    public void setStatus(String status) {
        this.status = status;
    }
}