package com.codingchallenge.locations.repository;

import com.codingchallenge.locations.entity.Location;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    @Query(value = """
            SELECT id, name, coordinates, ST_Distance(coordinates, ST_SetSRID(ST_Point(:x, :y), 0)) AS distance
            FROM locations
            WHERE ST_Distance(coordinates, ST_SetSRID(ST_Point(:x, :y), 0)) <= radius
            AND type=:type
            ORDER BY distance
            """, nativeQuery = true)
    List<Object[]> findVisibleLocationsByTypeAndDistance(
            @Param("x") Integer x,
            @Param("y") Integer y,
            @Param("type") String type
    );
}
