package com.codingchallenge.locations.service;

import com.codingchallenge.locations.entity.Location;
import com.codingchallenge.locations.enums.LocationType;
import com.codingchallenge.locations.mapper.LocationMapper;
import com.codingchallenge.locations.repository.LocationRepository;
import com.codingchallenge.locations.vo.CreateLocationDTO;
import com.codingchallenge.locations.vo.LocationDTO;
import com.codingchallenge.locations.vo.NearbyLocationsResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
        List<Object[]> rows = repository
                .findVisibleLocationsByTypeAndDistance(x, y, LocationType.RESTAURANT
                        .name());
        return mapper.toNearbyLocationsResponse(rows, x, y);
    }

    public LocationDTO getLocationById(UUID id) {
        return repository.findById(id)
                .map(mapper::toLocationVO)
                .orElse(null);
    }

    public List<LocationDTO> createLocations(List<CreateLocationDTO> locations) {
        List<Location> savedLocations = repository.saveAll(locations.stream().map(mapper::toLocationEntity)
                .collect(Collectors.toList()));
        return savedLocations.stream().map(mapper::toLocationVO).collect(Collectors.toList());
    }

    public LocationDTO createLocation(CreateLocationDTO dto) {
        return mapper.toLocationVO(repository.save(mapper.toLocationEntity(dto)));
    }
}
