package com.example.SunriseSunset.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**Entity representing a location in the database.*/
@Entity
@Table(name = "locations")
public class LocationEntity {

    /** The unique identifier of the location. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer id;

    /** The name of the location. */
    @Column(name = "name", nullable = false)
    public String name;

    /** The country of the location. */
    @Column(name = "country")
    public String country;

    /** List of sunrise and sunset entities associated with this location. */
    @ManyToMany
    @JoinTable(
            name = "sunrise_sunset_locations",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "sunrise_sunset_id")
    )
    public List<SunriseSunsetEntity> sunriseSunsets = new ArrayList<>();
}