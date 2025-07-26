package com.codingchallenge.locations.mapper;

import com.codingchallenge.locations.entity.Location;
import com.codingchallenge.locations.vo.LocationDTO;
import com.codingchallenge.locations.vo.LocationSummary;
import com.codingchallenge.locations.vo.NearbyLocationsResponse;
import java.util.List;
import java.util.UUID;
import org.geolatte.geom.C2D;
import org.geolatte.geom.Point;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    default LocationDTO toLocationVO(Location location) {
        LocationDTO locationDTO = new LocationDTO(location.getId(), location.getName(),
                location.getType().name(), location.getOpeningHours(), location.getImage(),
                formatCoordinates(location.getCoordinates()));
        return locationDTO;
    }

    default LocationSummary toLocationItem(Object[] row) {
        LocationSummary locationSummary = new LocationSummary();
        locationSummary.setId((UUID) row[0]);
        locationSummary.setName((String) row[1]);
        locationSummary.setCoordinates(formatCoordinates((Point<C2D>) row[2]));
        locationSummary.setDistance((Double) row[3]);
        return locationSummary;
    }

    // ---- Custom default method for wrapping ----
    default NearbyLocationsResponse toNearbyLocationsResponse(List<Object[]> rows, Integer userX,
            Integer userY) {
        List<LocationSummary> items = rows.stream()
                .map(this::toLocationItem)
                .toList();

        NearbyLocationsResponse response = new NearbyLocationsResponse();
        response.setUserLocation("x=" + userX + ",y=" + userY);
        response.setLocations(items);
        return response;
    }

    // ---- Helper method ----
    default String formatCoordinates(Point<C2D> point) {
        return "x=" + (int) point.getPosition().getCoordinate(0) + ",y=" + (int) point.getPosition()
                .getCoordinate(1);
    }
}

