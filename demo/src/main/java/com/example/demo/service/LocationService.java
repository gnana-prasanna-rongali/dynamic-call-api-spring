package com.example.demo.service;

import com.example.demo.model.Location;
import com.example.demo.repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    @Autowired
    private LocationRepo locationRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.api.base-url}")
    private String baseUrl;

    public void saveLocation(Location location) {
        locationRepository.save(location);
        logger.info("Saved location: " + location);
    }

    public Location getLocationFromApi(String locationQueryParam) {
        String apiUrl=baseUrl+"?location="+ locationQueryParam;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Location> responseEntity = restTemplate.getForEntity(apiUrl, Location.class);
            Location locations = responseEntity.getBody();
            // Log the response
            if (locations != null) {
                logger.info("Received locations from API: {}", locations);

            } else {
                logger.warn("API Response is null");
            }
            return locations;
        } catch (Exception e) {
            logger.error("Error calling external API", e);
            return new Location();
        }
    }

    public Location addLocation(Location location) {
        saveLocation(location);
        return location;
    }

    public Location getLocationData(String locationQueryParam) {
        return getLocationFromApi(locationQueryParam);
    }

    public Location updateLocation(String id, Location locationDetails) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            location.setLat(locationDetails.getLat());
            location.setLng(locationDetails.getLng());
            location.setPlaceId(locationDetails.getPlaceId());
            location.setFormattedAddress(locationDetails.getFormattedAddress());
            return locationRepository.save(location);
        } else {
            throw new RuntimeException("Location not found with id " + id);
        }
    }

    public void deleteLocation(String id) {
        locationRepository.deleteById(id);
    }
}
