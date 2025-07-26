# Locations API

A Spring Boot REST API to manage and query **locations** (restaurants, cafes, bars) using **PostGIS** for spatial data and **Geolatte** for geometry handling.  
Supports storing locations on a **Cartesian plane** with single or bulk creation, querying by distance, and retrieving by ID.

---

## Features
- Add new location(s):
  - `POST /locations` → Single
  - `POST /locations/bulk` → Bulk
- Get location by ID: `GET /locations/{id}`
- Query nearby locations by coordinates: `GET /locations/search?x=&y=`
- Store coordinates as **PostGIS geometry(Point, 0)**
- API documentation available at **Swagger UI** → `/swagger-ui.html`

---

## Tech Stack
- **Java 17**
- **Spring Boot 3.2.x**
- **Spring Data JPA**
- **PostgreSQL + PostGIS**
- **Hibernate Spatial + Geolatte**
- **Lombok**
- **Swagger (springdoc-openapi)**
- **JUnit 5 / Mockito**

---

## Why PostgreSQL + PostGIS?
- **PostgreSQL** → General-purpose relational database.
- **PostGIS** → Adds geospatial capabilities:
  - Handles geometry types (points, lines, polygons)
  - Provides advanced spatial functions (e.g., `ST_Distance`, `ST_Within`, `ST_Buffer`)
  - Enables spatial indexes for fast geolocation queries

**Result:** Efficient nearby location queries and advanced geospatial analytics.

### Enable PostGIS
```sql
CREATE DATABASE locationsdb;
CREATE EXTENSION IF NOT EXISTS postgis;

CREATE DATABASE testdb;
CREATE EXTENSION IF NOT EXISTS postgis;
```


###  Getting Started
Clone the repository
```bash
git clone https://github.com/LavanyaKgowda01/locations.git
cd locations
```
Run the application
```bash
mvn spring-boot:run
```

### Run Tests
```bash
Run all unit and integration tests:
mvn test
```

### Sample JSON Data
This project includes a sample JSON file with predefined location entries for testing and seeding the database.

File Location: src/main/resources/sample.json

View on GitHub: sample.json

Usage
The file is automatically read on application startup (via a CommandLineRunner) and persisted to the database:

```java
  @Configuration
  public class DataLoaderConfig {
  
    @Bean
    CommandLineRunner runner(LocationRepository repo,
            ObjectMapper objectMapper,
            LocationMapper locationMapper) {
      return args -> {
        // Load sample.json from resources folder
        InputStream input = new ClassPathResource("sample.json").getInputStream();
  
        // Parse JSON file into list of DTOs
        List<CreateLocationDTO> locations = objectMapper.readValue(
                input, new TypeReference<>() {}
        );
  
        // Convert DTO -> Entity and save to DB
        repo.saveAll(
                locations.stream()
                        .map(locationMapper::toLocationEntity)
                        .toList()
        );
      };
    }
  }
```


### API Documentation
| HTTP Method | Endpoint                  | Description                     |
| ----------- | ------------------------- | ------------------------------- |
| POST        | `/locations/create`       | Add single location             |
| POST        | `/locations/bulk`         | Add multiple locations          |
| GET         | `/locations/{id}`         | Get location by ID              |
| GET         | `/locations/search?x=&y=` | Find locations near coordinates |

Swagger UI:
To view the API documentation, navigate to: http://localhost:8080/swagger-ui/index.html


