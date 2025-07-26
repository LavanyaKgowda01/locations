package com.codingchallenge.locations.service;

import static org.geolatte.geom.builder.DSL.point;

import com.codingchallenge.locations.entity.Location;
import com.codingchallenge.locations.enums.LocationType;
import com.codingchallenge.locations.mapper.LocationMapper;
import com.codingchallenge.locations.repository.LocationRepository;
import com.codingchallenge.locations.vo.LocationDTO;
import com.codingchallenge.locations.vo.CreateLocationDTO;
import com.codingchallenge.locations.vo.NearbyLocationsResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.geolatte.geom.C2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository repository;
    private final LocationMapper mapper;

    public LocationService(LocationRepository repository, LocationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public NearbyLocationsResponse getVisibleLocationsByUserCoordinates(Integer x, Integer y) {
        //HARDCODED to filter LocationType by RESTAURANT
        List<Object[]> rows = repository.findVisibleLocationsByTypeAndDistance(x, y, LocationType.RESTAURANT
                .name());
        return mapper.toNearbyLocationsResponse(rows, x, y);
    }

    public LocationDTO getLocationById(UUID id) {
        return repository.findById(id)
                .map(mapper::toLocationVO)
                .orElse(null);
    }

    public List<Location> createLocations(List<CreateLocationDTO> locations) {
        List<Location> locationList = new ArrayList<>();
        for (CreateLocationDTO dto : locations) {
            Location location = new Location();
            location.setName(dto.getName());
            location.setOpeningHours(dto.getOpeningHours());
            location.setImage(dto.getImage());
            location.setRadius(dto.getRadius());
            location.setType(LocationType.valueOf(dto.getType().toUpperCase()));
            C2D coords = new C2D(dto.getX(), dto.getY());
            Point<C2D> point = point(CoordinateReferenceSystems.PROJECTED_2D_METER, coords);
            location.setCoordinates(point);
            locationList.add(repository.save(location));
        }
        return locationList;
    }

    public LocationDTO createLocation(CreateLocationDTO dto) {
            Location location = new Location();
            location.setName(dto.getName());
            location.setOpeningHours(dto.getOpeningHours());
            location.setImage(dto.getImage());
            location.setRadius(dto.getRadius());
            location.setType(LocationType.valueOf(dto.getType().toUpperCase()));
            C2D coords = new C2D(dto.getX(), dto.getY());
            Point<C2D> point = point(CoordinateReferenceSystems.PROJECTED_2D_METER, coords);
            location.setCoordinates(point);

        return mapper.toLocationVO(repository.save(location));
    }
}
