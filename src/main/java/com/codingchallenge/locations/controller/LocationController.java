package com.codingchallenge.locations.controller;

import com.codingchallenge.locations.service.LocationService;
import com.codingchallenge.locations.vo.CreateLocationDTO;
import com.codingchallenge.locations.vo.LocationDTO;
import com.codingchallenge.locations.vo.NearbyLocationsResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/create")
    public ResponseEntity<LocationDTO> createLocation(@RequestBody CreateLocationDTO location) {
        LocationDTO locationDTO = locationService.createLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<NearbyLocationsResponse> getVisibleLocationsByUserCoordinates(
            @RequestParam Integer x,
            @RequestParam Integer y
    ) {
        try {
            NearbyLocationsResponse response = locationService
                    .getVisibleLocationsByUserCoordinates(x, y);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable UUID id) {
        LocationDTO locationDTO = locationService.getLocationById(id);
        if (locationDTO != null) {
            return new ResponseEntity<>(locationDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> uploadLocations(@RequestBody List<CreateLocationDTO> locations) {
        locationService.createLocations(locations);
        return ResponseEntity.ok(" locations imported successfully");
    }


}
