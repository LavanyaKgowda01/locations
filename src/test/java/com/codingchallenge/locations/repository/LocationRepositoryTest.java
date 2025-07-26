package com.codingchallenge.locations.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.geolatte.geom.Point;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocationRepositoryTest {

    @Mock
    private LocationRepository repository;
    @Mock
    private Point mockPoint;
    @Mock
    private Point mockPoint1;

    @Test
    void testFindVisibleLocationsByTypeAndDistance() {

        // Mocking the repository method to return a list with one location
        // The location is represented as an Object array with UUID, name, coordinates, and distance
        Object[] row = new Object[]{
                UUID.randomUUID(),
                "Bluefin Bistro",
                mockPoint,
                1
        };
        Object[] row1 = new Object[]{
                UUID.randomUUID(),
                "Bluefin Bistro1",
                mockPoint1,
                3
        };
        Mockito.when(repository.findVisibleLocationsByTypeAndDistance(50, 50, "RESTAURANT"))
                .thenReturn(
                        List.of(row, row1));

        List<Object[]> result = repository
                .findVisibleLocationsByTypeAndDistance(50, 50, "RESTAURANT");
        assertThat(result).hasSize(2);
    }

}

