package com.codingchallenge.locations;


import static org.assertj.core.api.Assertions.assertThat;
import static org.geolatte.geom.builder.DSL.point;
import com.codingchallenge.locations.entity.Location;
import com.codingchallenge.locations.enums.LocationType;
import com.codingchallenge.locations.mapper.LocationMapper;
import com.codingchallenge.locations.repository.LocationRepository;
import com.codingchallenge.locations.vo.LocationSummary;
import com.codingchallenge.locations.vo.NearbyLocationsResponse;
import java.util.List;
import java.util.UUID;
import org.geolatte.geom.C2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class LocationsApplicationTests {

	@Autowired
	private LocationMapper locationMapper;
	@Autowired
	private LocationRepository locationRepository;


	@BeforeEach
	void setUp() {
		locationRepository.deleteAll();

		locationRepository.save(
				new Location(UUID.randomUUID(), "Bluefin Bistro 10",
						createPoint(50, 50),
						4,
						"https://tinyurl.com/location10",
						"10:00AM-11:00PM",
						LocationType.RESTAURANT)
		);
		locationRepository.save(
				new Location(UUID.randomUUID(), "Harvest Moon Cafe 6",
						createPoint(49, 51),
						2,
						"https://tinyurl.com/location6",
						"10:00AM-11:00PM",
						LocationType.RESTAURANT)
		);
		locationRepository.save(
				new Location(UUID.randomUUID(), "Sunset Kitchen 3",
						createPoint(53, 52),
						5,
						"https://tinyurl.com/location3",
						"10:00AM-11:00PM",
						LocationType.RESTAURANT)
		);
		locationRepository.save(
				new Location(UUID.randomUUID(), "Casa Verde 2",
						createPoint(44, 59),
						2,
						"https://tinyurl.com/location2",
						"10:00AM-11:00PM",
						LocationType.RESTAURANT)
		);
	}


	private Point createPoint(double x, double y) {
		C2D c2d = new C2D(x, y);
		Point<C2D> point = point(CoordinateReferenceSystems.PROJECTED_2D_METER, c2d);
		return point;
	}

	@Test
	void testFindWithinRadius() {
		Integer x = 50;
		Integer y = 50;
		List<Object[]> results = locationRepository
				.findVisibleLocationsByTypeAndDistance(x, y, LocationType.RESTAURANT.name());
		NearbyLocationsResponse response = locationMapper.toNearbyLocationsResponse(results, x, y);
		assertThat(response.getLocations()).extracting(
				LocationSummary::getName)
				.containsExactlyInAnyOrder("Bluefin Bistro 10", "Harvest Moon Cafe 6",
						"Sunset Kitchen 3");
		assertThat(response.getLocations()).hasSize(3);
	}
}

