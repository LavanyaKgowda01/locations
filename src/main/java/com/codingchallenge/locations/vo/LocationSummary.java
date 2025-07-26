package com.codingchallenge.locations.vo;

import java.util.UUID;
import lombok.Data;

@Data
public class LocationSummary {

    private UUID id;
    private String name;
    private String coordinates;
    private double distance;
}
