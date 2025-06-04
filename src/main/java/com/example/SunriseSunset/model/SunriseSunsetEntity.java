package com.example.SunriseSunset.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**Entity representing sunrise and sunset data in the database.*/
@Entity
@Table(name = "sunrise_and_sunset")
public class SunriseSunsetEntity {

    /** The unique identifier of the sunrise and sunset entry. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer id;

    /** The date of the sunrise and sunset. */
    @Column(name = "date", nullable = false)
    public LocalDate date;

    /** The latitude of the location. */
    @Column(name = "latitude", nullable = false)
    public Double latitude;

    /** The longitude of the location. */
    @Column(name = "longitude", nullable = false)
    public Double longitude;

    /** The sunrise time. */
    @Column(name = "sunrise")
    public OffsetDateTime sunrise;

    /** The sunset time. */
    @Column(name = "sunset")
    public OffsetDateTime sunset;

    /** List of locations associated with this sunrise and sunset entry. */
    @ManyToMany
    @JoinTable(
            name = "sunrise_sunset_locations",
            joinColumns = @JoinColumn(name = "sunrise_sunset_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    public List<LocationEntity> locations = new ArrayList<>();
}