package com.example.SunriseSunset.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**Represents the sunrise and sunset times returned by the external API.*/
public class Results {

    /** The sunrise time. */
    private String sunrise;

    /** The sunset time. */
    private String sunset;

    /**Gets the sunrise time.*/
    @JsonProperty("sunrise")
    public String getSunrise() {
        return sunrise;
    }

    /**Gets the sunset time.*/
    @JsonProperty("sunset")
    public String getSunset() {
        return sunset;
    }
}