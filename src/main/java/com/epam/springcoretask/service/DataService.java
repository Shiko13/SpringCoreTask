package com.epam.springcoretask.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;

public interface DataService<T> {
    void initializeData(List<T> data);

    Class<T> getDataType();

    String getDataPath();

    default void initData(ResourceLoader loader, ObjectMapper mapper) throws IOException {
        Class<T> dataType = getDataType();
        JavaType dataTypeReference = mapper.getTypeFactory().constructParametricType(List.class, dataType);
        List<T> data = mapper.readValue(
                loader.getResource(getDataPath()).getInputStream(), dataTypeReference
        );
        initializeData(data);
    }
}