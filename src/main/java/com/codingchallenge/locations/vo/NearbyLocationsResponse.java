package com.codingchallenge.locations.vo;

import java.util.List;
import lombok.Data;

@Data
public class NearbyLocationsResponse {

    private String userLocation;
    private List<LocationSummary> locations;
}
