package com.codingchallenge.locations.service;
import com.codingchallenge.locations.entity.Location;
import com.codingchallenge.locations.enums.LocationType;
import com.codingchallenge.locations.mapper.LocationMapper;
import com.codingchallenge.locations.repository.LocationRepository;
import com.codingchallenge.locations.vo.CreateLocationDTO;
import com.codingchallenge.locations.vo.LocationDTO;
import com.codingchallenge.locations.vo.NearbyLocationsResponse;
import org.geolatte.geom.C2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.geolatte.geom.builder.DSL.point;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository repository;

    @Mock
    private LocationMapper mapper;

    @InjectMocks
    private LocationService service;

    private UUID locationId;
    private Location locationEntity;
    private LocationDTO locationDTO;

    @BeforeEach
    void setUp() {
        locationId = UUID.randomUUID();
        locationEntity = new Location();
        locationEntity.setId(locationId);
        locationEntity.setName("Test Location");
        locationEntity.setType(LocationType.RESTAURANT);
        locationEntity.setOpeningHours("10:00AM-11:00PM");
        locationEntity.setImage("https://test.com");

        locationDTO = new LocationDTO(locationId, "Test Location",
                LocationType.RESTAURANT.name(),
                "10:00AM-11:00PM",
                "https://test.com", "x=10,y=20");
    }

    @Test
    void testGetVisibleLocationsByUserCoordinates() {
        C2D c2d = new C2D(30, 20);
        Point<C2D> point = point(CoordinateReferenceSystems.PROJECTED_2D_METER, c2d);
        Object[] row = new Object[]{
                UUID.randomUUID(),
                "Bluefin Bistro",
                point,
                1
        };
        C2D c2d1 = new C2D(10, 60);
        Point<C2D> point1 = point(CoordinateReferenceSystems.PROJECTED_2D_METER, c2d1);
        Object[] row1 = new Object[]{
                UUID.randomUUID(),
                "Bluefin Bistro1",
                point1,
                3
        };
        List<Object[]> rows = List.of(row, row1);
        NearbyLocationsResponse response = new NearbyLocationsResponse();
        response.setUserLocation("x=10,y=20");

        when(repository.findVisibleLocationsByTypeAndDistance(10, 20, LocationType.RESTAURANT.name()))
                .thenReturn(rows);
        when(mapper.toNearbyLocationsResponse(rows, 10, 20)).thenReturn(response);

        NearbyLocationsResponse result = service.getVisibleLocationsByUserCoordinates(10, 20);

        assertThat(result).isNotNull();
        assertThat(result.getUserLocation()).isEqualTo("x=10,y=20");
        verify(repository).findVisibleLocationsByTypeAndDistance(10, 20, LocationType.RESTAURANT.name());
        verify(mapper).toNearbyLocationsResponse(rows, 10, 20);
    }

    @Test
    void testGetLocationById_found() {
        when(repository.findById(locationId)).thenReturn(Optional.of(locationEntity));
        when(mapper.toLocationVO(locationEntity)).thenReturn(locationDTO);

        LocationDTO result = service.getLocationById(locationId);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Location");
        verify(repository).findById(locationId);
        verify(mapper).toLocationVO(locationEntity);
    }

    @Test
    void testGetLocationById_notFound() {
        when(repository.findById(locationId)).thenReturn(Optional.empty());

        LocationDTO result = service.getLocationById(locationId);

        assertThat(result).isNull();
        verify(repository).findById(locationId);
        verifyNoInteractions(mapper);
    }

    @Test
    void testCreateLocations() {
        CreateLocationDTO createDTO = new CreateLocationDTO();
        Location entity = new Location();

        when(mapper.toLocationEntity(createDTO)).thenReturn(entity);
        when(repository.saveAll(List.of(entity))).thenReturn(List.of(locationEntity));
        when(mapper.toLocationVO(locationEntity)).thenReturn(locationDTO);

        List<LocationDTO> result = service.createLocations(List.of(createDTO));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Location");
        verify(repository).saveAll(anyList());
    }

    @Test
    void testCreateLocation() {
        CreateLocationDTO createDTO = new CreateLocationDTO();
        Location entity = new Location();

        when(mapper.toLocationEntity(createDTO)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(locationEntity);
        when(mapper.toLocationVO(locationEntity)).thenReturn(locationDTO);

        LocationDTO result = service.createLocation(createDTO);

        assertThat(result.getName()).isEqualTo("Test Location");
        verify(repository).save(entity);
    }
}
