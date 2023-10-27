package com.epam.springcoretask.config;

import com.epam.springcoretask.service.TraineeService;
import com.epam.springcoretask.service.TrainerService;
import com.epam.springcoretask.service.TrainingService;
import com.epam.springcoretask.service.TrainingTypeService;
import com.epam.springcoretask.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
public class DataInitializationConfig {

    private final String traineeDataPath;
    private final String trainerDataPath;
    private final String trainingDataPath;
    private final String trainingTypeDataPath;
    private final String userDataPath;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public DataInitializationConfig(
            @Value("${data.path.trainee}") String traineeDataPath,
            @Value("${data.path.trainer}") String trainerDataPath,
            @Value("${data.path.training}") String trainingDataPath,
            @Value("${data.path.trainingType}") String trainingTypeDataPath,
            @Value("${data.path.user}") String userDataPath,
            TraineeService traineeService,
            TrainerService trainerService,
            TrainingService trainingService,
            UserService userService,
            TrainingTypeService trainingTypeService,
            ResourceLoader resourceLoader) {
        this.traineeDataPath = traineeDataPath;
        this.trainerDataPath = trainerDataPath;
        this.trainingDataPath = trainingDataPath;
        this.trainingTypeDataPath = trainingTypeDataPath;
        this.userDataPath = userDataPath;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.userService = userService;
        this.trainingTypeService = trainingTypeService;
        this.resourceLoader = resourceLoader;
    }


    @PostConstruct
    public void initializeData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            userService.initializeData(objectMapper.readValue(resourceLoader.getResource(userDataPath).getInputStream(),
                    new TypeReference<>() {
                    }));

            trainingTypeService.initializeData(objectMapper.readValue(resourceLoader.getResource(trainingTypeDataPath).getInputStream(),
                    new TypeReference<>() {
                    }));

            traineeService.initializeData(objectMapper.readValue(resourceLoader.getResource(traineeDataPath).getInputStream(),
                    new TypeReference<>() {
                    }));

            trainerService.initializeData(objectMapper.readValue(resourceLoader.getResource(trainerDataPath).getInputStream(),
                    new TypeReference<>() {
                    }));

            trainingService.initializeData(objectMapper.readValue(resourceLoader.getResource(trainingDataPath).getInputStream(),
                    new TypeReference<>() {
                    }));

        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data from files.", e);
        }
    }
}
