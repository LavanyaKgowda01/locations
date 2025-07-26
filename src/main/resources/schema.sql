DROP TABLE IF EXISTS locations;

-- Create table
CREATE TABLE locations (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    coordinates geometry(Point, 0) NOT NULL,
    radius DOUBLE PRECISION,
    image VARCHAR(255),
    opening_hours VARCHAR(255),
    type VARCHAR(50)
);

CREATE INDEX idx_locations_coordinates
    ON locations
    USING GIST (coordinates);
