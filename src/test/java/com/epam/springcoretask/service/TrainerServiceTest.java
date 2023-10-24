package com.epam.springcoretask.service;

import com.epam.springcoretask.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TraineeServiceTest {

    @Autowired
    private TraineeService traineeService;

    @BeforeEach
    public void setup() {
        traineeService.clearData();
    }

    @Test
    public void testSaveAndRetrieveTrainee() {
        Trainee trainee = new Trainee(1L, 2L);

        traineeService.save(trainee);

        Optional<Trainee> retrievedTrainee = traineeService.get(trainee.getId());

        assertTrue(retrievedTrainee.isPresent());
        assertEquals(trainee.getId(), retrievedTrainee.get().getId());
        assertEquals(trainee.getUserId(), retrievedTrainee.get().getUserId());
    }

    @Test
    public void testGetAllTrainees() {

        Collection<Trainee> trainees = traineeService.getAll();
        assertTrue(trainees.isEmpty());

        Trainee trainee1 = new Trainee(1L, 2L);
        Trainee trainee2 = new Trainee(2L, 3L);
        traineeService.save(trainee1);
        traineeService.save(trainee2);

        trainees = traineeService.getAll();

        assertEquals(2, trainees.size());
        assertTrue(trainees.contains(trainee1));
        assertTrue(trainees.contains(trainee2));
    }

    @Test
    public void testUpdateTrainee() {
        Trainee trainee = new Trainee(1L, 2L);

        traineeService.save(trainee);

        trainee.setUserId(3L);
        traineeService.update(trainee);

        Optional<Trainee> updatedTrainee = traineeService.get(trainee.getId());

        assertTrue(updatedTrainee.isPresent());
        assertEquals(trainee.getId(), updatedTrainee.get().getId());
        assertEquals(trainee.getUserId(), updatedTrainee.get().getUserId());
    }

    @Test
    public void testDeleteTrainee() {
        Trainee trainee = new Trainee(1L, 2L);

        traineeService.save(trainee);

        traineeService.delete(trainee);

        Optional<Trainee> deletedTrainee = traineeService.get(trainee.getId());
        assertTrue(deletedTrainee.isEmpty());
    }
}

