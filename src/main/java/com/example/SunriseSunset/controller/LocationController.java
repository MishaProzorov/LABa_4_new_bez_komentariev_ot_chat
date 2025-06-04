package com.example.SunriseSunset.controller;

import com.example.SunriseSunset.dto.LocationDTO;
import com.example.SunriseSunset.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**REST controller for managing location-related operations.*/
@RestController
@RequestMapping("/locations")
@Tag(name = "Location Controller", description = "API for managing locations")
public class LocationController {

    /** Service for handling location-related business logic. */
    private final LocationService locationService;

    /**Constructs a LocationController with the specified LocationService.*/
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**Creates a new location.*/
    @Operation(summary = "Create a new location", responses = {
        @ApiResponse(responseCode = "200", description = "Location created successfully",
                    content = @Content(schema = @Schema(implementation = LocationDTO.class)))})
    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO dto) {
        LocationDTO savedDto = locationService.createLocation(dto);
        return ResponseEntity.ok(savedDto);
    }

    /**Retrieves a location by its ID.*/
    @Operation(summary = "Get location by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Location found",
                    content = @Content(schema = @Schema(implementation = LocationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Location not found")})
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(
        @Parameter(description = "ID of the location to be retrieved") @PathVariable Integer id) {
        LocationDTO dto = locationService.getLocationById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    /**Retrieves all locations.*/
    @Operation(summary = "Get all locations", responses = {
        @ApiResponse(responseCode = "200", description = "List of all locations",
                    content = @Content(schema = @Schema(implementation = LocationDTO.class)))})
    @GetMapping("/all")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    /**Updates an existing location.*/
    @Operation(summary = "Update location", responses = {
        @ApiResponse(responseCode = "200", description = "Location updated successfully",
                    content = @Content(schema = @Schema(implementation = LocationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Location not found")})
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(
        @Parameter(description = "ID of the location to be updated") @PathVariable Integer id,
        @RequestBody LocationDTO dto) {
        LocationDTO updatedDto = locationService.updateLocation(id, dto);
        return updatedDto != null ? ResponseEntity.ok(updatedDto) : ResponseEntity.notFound().build();
    }

    /**Deletes a location by its ID.*/
    @Operation(summary = "Delete location", responses = {
        @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Location not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(
            @Parameter(description = "ID of the location to be deleted") @PathVariable Integer id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}