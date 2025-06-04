package com.example.SunriseSunset.service;

import com.example.SunriseSunset.dto.LocationDTO;
import com.example.SunriseSunset.model.LocationEntity;
import com.example.SunriseSunset.model.SunriseSunsetEntity;
import com.example.SunriseSunset.repository.LocationRepository;
import com.example.SunriseSunset.repository.SunriseSunsetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**Service class for managing location-related operations.*/
@Service
@SuppressWarnings("unchecked")
public class LocationService {

    /** Logger instance for logging service operations. */
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    /** Repository for location-related database operations. */
    private final LocationRepository locationRepository;

    /** Repository for sunrise and sunset-related database operations. */
    private final SunriseSunsetRepository sunriseSunsetRepository;

    /** Cache for storing location-related data. */
    private final Map<String, Object> entityCache;

    /**Constructs a LocationService with the specified dependencies.*/
    @Autowired
    public LocationService(LocationRepository locationRepository,
                           SunriseSunsetRepository sunriseSunsetRepository,
                           Map<String, Object> entityCache) {
        this.locationRepository = locationRepository;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
        this.entityCache = entityCache;
    }

    /**Creates a new location.*/
    public LocationDTO createLocation(LocationDTO dto) {
        LocationEntity entity = new LocationEntity();
        entity.name = dto.getName();
        entity.country = dto.getCountry();

        if (dto.getSunriseSunsetIds() != null && !dto.getSunriseSunsetIds().isEmpty()) {
            List<SunriseSunsetEntity> sunriseSunsets = sunriseSunsetRepository.findAllById(dto.getSunriseSunsetIds());
            entity.sunriseSunsets = sunriseSunsets;
        }

        LocationEntity savedEntity = locationRepository.save(entity);
        LocationDTO savedDto = convertToDTO(savedEntity);

        logger.info("Caching Location with ID {} after creation", savedEntity.id);
        entityCache.put("Location_" + savedEntity.id, savedDto);
        entityCache.remove("Location_All");

        return savedDto;
    }

    /**Retrieves a location by its ID.*/
    public LocationDTO getLocationById(Integer id) {
        String cacheKey = "Location_" + id;
        if (entityCache.containsKey(cacheKey)) {
            logger.debug("Cache hit for Location ID {}", id);
            return (LocationDTO) entityCache.get(cacheKey);
        }

        logger.debug("Cache miss for Location ID {}, querying database", id);
        LocationEntity entity = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + id));

        LocationDTO dto = convertToDTO(entity);
        logger.info("Caching Location with ID {} after database query", id);
        entityCache.put(cacheKey, dto);

        return dto;
    }

    /**Retrieves all locations.*/
    public List<LocationDTO> getAllLocations() {
        String cacheKey = "Location_All";
        if (entityCache.containsKey(cacheKey)) {
            logger.debug("Cache hit for all Locations");
            return (List<LocationDTO>) entityCache.get(cacheKey);
        }

        logger.debug("Cache miss for all Locations, querying database");
        List<LocationEntity> entities = locationRepository.findAll();
        List<LocationDTO> dtos = entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        for (LocationDTO dto : dtos) {
            logger.info("Caching Location with ID {} after database query", dto.getId());
            entityCache.put("Location_" + dto.getId(), dto);
        }

        logger.info("Caching all Locations under Location_All");
        entityCache.put(cacheKey, dtos);

        return dtos;
    }

    /**Updates an existing location.*/
    public LocationDTO updateLocation(Integer id, LocationDTO dto) {
        LocationEntity entity = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + id));

        entity.name = dto.getName();
        entity.country = dto.getCountry();

        if (dto.getSunriseSunsetIds() != null && !dto.getSunriseSunsetIds().isEmpty()) {
            List<SunriseSunsetEntity> sunriseSunsets = sunriseSunsetRepository.findAllById(dto.getSunriseSunsetIds());
            entity.sunriseSunsets = sunriseSunsets;
        } else {
            entity.sunriseSunsets.clear();
        }

        LocationEntity updatedEntity = locationRepository.save(entity);
        LocationDTO updatedDto = convertToDTO(updatedEntity);

        logger.info("Updating cache for Location with ID {}", id);
        entityCache.put("Location_" + id, updatedDto);
        entityCache.remove("Location_All");

        return updatedDto;
    }

    /**Deletes a location by its ID.*/
    public void deleteLocation(Integer id) {
        if (!locationRepository.existsById(id)) {
            throw new IllegalArgumentException("Location not found with id: " + id);
        }

        locationRepository.deleteById(id);
        logger.info("Removing Location with ID {} from cache", id);
        entityCache.remove("Location_" + id);
        entityCache.remove("Location_All");
    }

    /**Converts a LocationEntity to a LocationDTO.*/
    private LocationDTO convertToDTO(LocationEntity entity) {
        List<Integer> sunriseSunsetIds = entity.sunriseSunsets.stream()
                .map(sunriseSunset -> sunriseSunset.id)
                .collect(Collectors.toList());
        return new LocationDTO(entity.id, entity.name, entity.country, sunriseSunsetIds);
    }
}