package com.example.SunriseSunset.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/**Data Transfer Object for representing sunrise and sunset data.*/
public class SunriseSunsetDTO {

    /** The unique identifier of the sunrise and sunset entry. */
    private Integer id;

    /** The date of the sunrise and sunset. */
    private LocalDate date;

    /** The latitude of the location. */
    private Double latitude;

    /** The longitude of the location. */
    private Double longitude;

    /** The sunrise time. */
    private OffsetDateTime sunrise;

    /** The sunset time. */
    private OffsetDateTime sunset;

    /** List of location IDs associated with this sunrise and sunset entry. */
    private List<Integer> locationIds;

    /**Default constructor for SunriseSunsetDTO.*/
    public SunriseSunsetDTO() {}

    /**Constructs a SunriseSunsetDTO with the specified date, latitude, and longitude.*/
    public SunriseSunsetDTO(LocalDate date, Double latitude, Double longitude) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**Constructs a SunriseSunsetDTO with all fields.*/
    public SunriseSunsetDTO(Integer id, LocalDate date, Double latitude, Double longitude,
                            OffsetDateTime sunrise, OffsetDateTime sunset, List<Integer> locationIds) {
        this.id = id;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.locationIds = locationIds;
    }

    /**Gets the ID of the sunrise and sunset entry.*/
    public Integer getId() {
        return id;
    }

    /**Sets the ID of the sunrise and sunset entry.*/
    public void setId(Integer id) {
        this.id = id;
    }

    /**Gets the date of the sunrise and sunset.*/
    public LocalDate getDate() {
        return date;
    }

    /**Sets the date of the sunrise and sunset.*/
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**Gets the latitude of the location.*/
    public Double getLatitude() {
        return latitude;
    }

    /**Sets the latitude of the location.*/
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**Gets the longitude of the location.*/
    public Double getLongitude() {
        return longitude;
    }

    /**Sets the longitude of the location.*/
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**Gets the sunrise time.*/
    public OffsetDateTime getSunrise() {
        return sunrise;
    }

    /**Sets the sunrise time.*/
    public void setSunrise(OffsetDateTime sunrise) {
        this.sunrise = sunrise;
    }

    /**Gets the sunset time.*/
    public OffsetDateTime getSunset() {
        return sunset;
    }

    /**Sets the sunset time.*/
    public void setSunset(OffsetDateTime sunset) {
        this.sunset = sunset;
    }

    /**Gets the list of associated location IDs.*/
    public List<Integer> getLocationIds() {
        return locationIds;
    }

    /**Sets the list of associated location IDs.*/
    public void setLocationIds(List<Integer> locationIds) {
        this.locationIds = locationIds;
    }
}