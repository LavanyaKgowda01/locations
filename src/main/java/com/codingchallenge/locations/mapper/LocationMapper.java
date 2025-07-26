package com.codingchallenge.locations.mapper;

import com.codingchallenge.locations.entity.Location;
import com.codingchallenge.locations.enums.LocationType;
import com.codingchallenge.locations.vo.CreateLocationDTO;
import com.codingchallenge.locations.vo.LocationDTO;
import com.codingchallenge.locations.vo.LocationSummary;
import com.codingchallenge.locations.vo.NearbyLocationsResponse;
import java.util.List;
import java.util.UUID;
import org.geolatte.geom.C2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import static org.geolatte.geom.builder.DSL.point;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    // --- Entity to DTO mapping ---
    default LocationDTO toLocationVO(Location location) {
        return new LocationDTO(location.getId(), location.getName(),
                location.getType().name(), location.getOpeningHours(), location.getImage(),
                formatCoordinates(location.getCoordinates()));
    }

    // --- Row -> LocationSummary (for native query result) ---
    @Named("toLocationSummary")
    default LocationSummary toLocationItem(Object[] row) {
        LocationSummary summary = new LocationSummary();
        summary.setId((UUID) row[0]);
        summary.setName((String) row[1]);
        summary.setCoordinates(formatCoordinates((Point<C2D>) row[2]));
        summary.setDistance((Double) row[3]);
        return summary;
    }

    // --- Wrap query result list into response ---
    default NearbyLocationsResponse toNearbyLocationsResponse(List<Object[]> rows, Integer userX, Integer userY) {
        List<LocationSummary> items = rows.stream()
                .map(this::toLocationItem)
                .toList();

        NearbyLocationsResponse response = new NearbyLocationsResponse();
        response.setUserLocation("x=" + userX + ",y=" + userY);
        response.setLocations(items);
        return response;
    }

    // --- Coordinates formatting ---
    @Named("formatCoordinates")
    default String formatCoordinates(Point<C2D> point) {
        return "x=" + (int) point.getPosition().getCoordinate(0) +
                ",y=" + (int) point.getPosition().getCoordinate(1);
    }

    default Location toLocationEntity(CreateLocationDTO dto) {
        Location location = new Location();
        location.setName(dto.getName());
        location.setType(LocationType.valueOf(dto.getType().toUpperCase()));
        location.setOpeningHours(dto.getOpeningHours());
        location.setImage(dto.getImage());
        location.setRadius(dto.getRadius());

        // Assuming coordinates are in the format "x=...,y=..."
        String[] coords = dto.getCoordinates().split(",");
        int x = Integer.parseInt(coords[0].split("=")[1]);
        int y = Integer.parseInt(coords[1].split("=")[1]);
        C2D c2d = new C2D(x, y);
        Point<C2D> point = point(CoordinateReferenceSystems.PROJECTED_2D_METER, c2d);
        location.setCoordinates(point);

        return location;
    }

}

