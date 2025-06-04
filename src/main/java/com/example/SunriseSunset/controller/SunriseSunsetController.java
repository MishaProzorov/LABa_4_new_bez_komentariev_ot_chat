package com.example.SunriseSunset.controller;

import com.example.SunriseSunset.dto.SunriseSunsetDTO;
import com.example.SunriseSunset.service.SunriseSunsetService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**REST controller for managing sunrise and sunset data.*/
@RestController
@RequestMapping("/sun/times")
public class SunriseSunsetController {

    /** Service for handling sunrise and sunset operations. */
    private final SunriseSunsetService sunService;

    /**Constructs a SunriseSunsetController with the specified SunriseSunsetService.*/
    @Autowired
    public SunriseSunsetController(SunriseSunsetService sunService) {
        this.sunService = sunService;
    }

    /**Creates a new sunrise and sunset entry.*/
    @PostMapping
    public ResponseEntity<SunriseSunsetDTO> createSunriseSunset(@RequestBody SunriseSunsetDTO dto) {
        SunriseSunsetDTO savedDto = sunService.createSunriseSunset(dto);
        return ResponseEntity.ok(savedDto);
    }

    /**Retrieves a sunrise and sunset entry by its ID.*/
    @GetMapping("/{id}")
    public ResponseEntity<SunriseSunsetDTO> getSunriseSunsetById(@PathVariable Integer id) {
        SunriseSunsetDTO dto = sunService.getSunriseSunsetById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    /**Retrieves all sunrise and sunset entries.*/
    @GetMapping("/all")
    public ResponseEntity<List<SunriseSunsetDTO>> getAllSunriseSunsets() {
        return ResponseEntity.ok(sunService.getAllSunriseSunsets());
    }

    /**Updates an existing sunrise and sunset entry.*/
    @PutMapping("/{id}")
    public ResponseEntity<SunriseSunsetDTO> updateSunriseSunset(@PathVariable Integer id, @RequestBody SunriseSunsetDTO dto) {
        SunriseSunsetDTO updatedDto = sunService.updateSunriseSunset(id, dto);
        return updatedDto != null ? ResponseEntity.ok(updatedDto) : ResponseEntity.notFound().build();
    }

    /**Deletes a sunrise and sunset entry by its ID.*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSunriseSunset(@PathVariable Integer id) {
        sunService.deleteSunriseSunset(id);
        return ResponseEntity.noContent().build();
    }

    /**Retrieves sunrise and sunset entries by location ID.*/
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<SunriseSunsetDTO>> getSunriseSunsetsByLocationId(@PathVariable Integer locationId) {
        List<SunriseSunsetDTO> dtos = sunService.getSunriseSunsetsByLocationId(locationId);
        return ResponseEntity.ok(dtos);
    }

    /**Retrieves sunrise and sunset entries by date and location name.*/
    @GetMapping("/by-date-and-location")
    public ResponseEntity<List<SunriseSunsetDTO>> getSunriseSunsetsByDateAndLocation(
            @RequestParam("date") String date,
            @RequestParam("locationName") String locationName) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<SunriseSunsetDTO> dtos = sunService.getSunriseSunsetsByDateAndLocationName(localDate, locationName);
            return ResponseEntity.ok(dtos);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}