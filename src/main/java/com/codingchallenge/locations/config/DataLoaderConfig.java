package com.codingchallenge.locations.config;

import com.codingchallenge.locations.mapper.LocationMapper;
import com.codingchallenge.locations.repository.LocationRepository;
import com.codingchallenge.locations.vo.CreateLocationDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

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
