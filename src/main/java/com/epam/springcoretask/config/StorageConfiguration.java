package com.epam.springcoretask.config;

import com.epam.springcoretask.model.Trainee;
import com.epam.springcoretask.model.Trainer;
import com.epam.springcoretask.model.Training;
import com.epam.springcoretask.model.TrainingType;
import com.epam.springcoretask.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class StorageConfiguration {

    @Bean
    public Map<Long, Trainer> trainerStorage() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Trainee> traineeStorage() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Training> trainingStorage() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, User> userStorage() {return new HashMap<>();}

    @Bean
    public Map<Long, TrainingType> trainingTypeStorage() {return new HashMap<>();}
}