package com.codingchallenge.locations.vo;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDTO {

    private UUID id;
    private String name;
    private String type;
    private String openingHours;
    private String image;
    private String coordinates;
}
