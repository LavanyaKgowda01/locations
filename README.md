# Locations API

A Spring Boot REST API to manage and query **locations** (restaurants, cafes, bars) using **PostGIS** for spatial data and **Geolatte** for geometry handling.  
Supports storing locations on a **Cartesian plane** with bulk creation, querying by distance, and retrieving by ID.

---

## **Features**
- Add new location(s) (`POST /locations` and `POST /locations/bulk`)
- Get location by ID (`GET /locations/{id}`)
- Query nearby locations based on user coordinates (`GET /locations/nearby?x=&y=`)
- Store coordinates as **PostGIS geometry(Point, 0)**
- Swagger UI for API documentation (`/swagger-ui.html`)

---

## **Tech Stack**
- **Java 17**
- **Spring Boot 3.2.x**
- **Spring Data JPA**
- **PostgreSQL + PostGIS**
- **Hibernate Spatial + Geolatte**
- **Lombok**
- **Swagger (springdoc-openapi)**
- **JUnit 5 / Mockito**

---

## **Prerequisites**
- Java 17+
- Maven 3.8+
- PostgreSQL with PostGIS enabled

---

## **Setup**
1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/locations.git
   cd locations
