package com.epam.springcoretask.util;

import com.epam.springcoretask.model.TrainingType;
import com.epam.springcoretask.model.User;
import com.epam.springcoretask.service.TraineeService;
import com.epam.springcoretask.service.TrainerService;
import com.epam.springcoretask.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
@AllArgsConstructor
public class CommandLineRunnerTest implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    private final TraineeService traineeService;

    private final TrainerService trainerService;

    private final TrainingService trainingService;

    private final Map<Long, TrainingType> trainingTypeStorage;

    private final Map<Long, User> userStorage;

    @Override
    public void run(String... args) {

        System.out.println("=====================INITIAL TRAINEES=====================");
        System.out.println();

        traineeService.getAll().forEach(System.out::println);

        System.out.println("=====================INITIAL TRAINERS=====================");

        trainerService.getAll().forEach(System.out::println);

        System.out.println("=====================INITIAL TRAININGS=====================");

        trainingService.getAll().forEach(System.out::println);

        System.out.println("=====================INITIAL TRAINING'S TYPES=====================");

        trainingTypeStorage.values().forEach(System.out::println);

        System.out.println("=====================INITIAL USERS=====================");

        userStorage.values().forEach(System.out::println);

        System.out.println("Custom Beans:");

        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Service.class);
        beans.putAll(applicationContext.getBeansWithAnnotation(Repository.class));

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            String beanName = entry.getKey();
            Object beanInstance = entry.getValue();
            String beanType = beanInstance.getClass().getName();

            System.out.println("Bean Name: " + beanName + ", Bean Type: " + beanType);
        }
    }
}
