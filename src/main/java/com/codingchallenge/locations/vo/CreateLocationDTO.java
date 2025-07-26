package com.codingchallenge.locations.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateLocationDTO {

    private String name;
    private String type;

    @JsonProperty("opening-hours")
    private String openingHours;

    private String image;

    private String coordinates;

    private double radius;

    public double getX() {
        // parse coordinates string "x=5,y=5"
        String[] parts = coordinates.split(",");
        return Double.parseDouble(parts[0].split("=")[1]);
    }

    public double getY() {
        String[] parts = coordinates.split(",");
        return Double.parseDouble(parts[1].split("=")[1]);
    }
}
