package com.epam.springcoretask.config;

import com.epam.springcoretask.service.DataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;

@Configuration
public class DataInitializationConfig {

    private final ResourceLoader resourceLoader;
    private final List<DataService<?>> dataServices;

    @Autowired
    public DataInitializationConfig(
            ResourceLoader resourceLoader,
            List<DataService<?>> dataServices) {
        this.resourceLoader = resourceLoader;
        this.dataServices = dataServices;
    }


    @PostConstruct
    public void initializeData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            for (DataService<?> dataService : dataServices) {
                dataService.initData(resourceLoader, objectMapper);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data from files.", e);
        }
    }
}
