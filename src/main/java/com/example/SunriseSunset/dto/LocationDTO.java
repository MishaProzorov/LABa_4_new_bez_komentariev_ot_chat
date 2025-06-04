package com.example.SunriseSunset.dto;

import java.util.List;

/**Data Transfer Object for representing location data.*/
public class LocationDTO {

    /** The unique identifier of the location. */
    private Integer id;

    /** The name of the location. */
    private String name;

    /** The country of the location. */
    private String country;

    /** List of sunrise and sunset entry IDs associated with the location. */
    private List<Integer> sunriseSunsetIds;

    /**Default constructor for LocationDTO.*/
    public LocationDTO() {}

    /**Constructs a LocationDTO with the specified parameters.*/
    public LocationDTO(Integer id, String name, String country, List<Integer> sunriseSunsetIds) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.sunriseSunsetIds = sunriseSunsetIds;
    }

    /**Gets the location ID.*/
    public Integer getId() { return id; }

    /**Sets the location ID.*/
    public void setId(Integer id) { this.id = id; }

    /**Gets the location name.*/
    public String getName() { return name; }

    /**Sets the location name.*/
    public void setName(String name) { this.name = name; }

    /**Gets the country of the location.*/
    public String getCountry() { return country; }

    /**Sets the country of the location.*/
    public void setCountry(String country) { this.country = country; }

    /**Gets the list of sunrise and sunset entry IDs.*/
    public List<Integer> getSunriseSunsetIds() { return sunriseSunsetIds; }

    /**Sets the list of sunrise and sunset entry IDs.*/
    public void setSunriseSunsetIds(List<Integer> sunriseSunsetIds) { this.sunriseSunsetIds = sunriseSunsetIds; }
}