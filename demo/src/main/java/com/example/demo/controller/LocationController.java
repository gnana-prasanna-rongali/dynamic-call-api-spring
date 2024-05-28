package com.example.demo.controller;

import com.example.demo.model.ApiResponse;
import com.example.demo.model.ApiError;
import com.example.demo.model.Location;
import com.example.demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/addlocation")
    public ResponseEntity<?> addLocation(@RequestBody Location location) {
        try {
            Location savedLocation = locationService.addLocation(location);
            return ResponseEntity.ok(new ApiResponse<>(true, "Location added successfully", savedLocation));
        } catch (Exception e)
        {
            return ResponseEntity.status(500).body(new ApiError("Error retrieving locations: " + e.getMessage()));
        }
    }

    @GetMapping("/getlocation")
    public ResponseEntity<?> getLocation(@RequestParam String location) {
        try {
            Location locations = locationService.getLocationData(location);
            return ResponseEntity.ok(new ApiResponse<>(true, "Locations retrieved successfully", locations));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiError("Error retrieving locations: " + e.getMessage()));
        }
    }

    @GetMapping("/newapicall")
    public ResponseEntity<?> callNewApi(@RequestParam String location) {
        try {
            Location locations = locationService.getLocationData(location);
            return ResponseEntity.ok(new ApiResponse<>(true, "Locations retrieved successfully", locations));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiError("Error retrieving locations: " + e.getMessage()));
        }
    }
    @PutMapping("/updatelocation/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable String id, @RequestBody Location locationDetails) {
        try {
            Location updatedLocation = locationService.updateLocation(id, locationDetails);
            return ResponseEntity.ok(new ApiResponse<>(true, "Location updated successfully", updatedLocation));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiError("Error updating location: " + e.getMessage()));
        }
    }
    @DeleteMapping("/deletelocation/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable String id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Location deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiError("Error deleting location: " + e.getMessage()));
        }
    }
}