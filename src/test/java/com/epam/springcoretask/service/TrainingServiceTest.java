package com.epam.springcoretask.service;

import com.epam.springcoretask.model.Trainer;
import com.epam.springcoretask.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TrainerServiceTest {

    @Autowired
    private TrainerService trainerService;

    @BeforeEach
    public void setup() {
        trainerService.clearData();
    }

    @Test
    public void saveTrainer_shouldOk() {
        Trainer trainer = new Trainer(1L, List.of(new TrainingType("Gym")), 2L);

        Trainer retrievedTrainee = trainerService.save(trainer);

        assertEquals(trainer.getId(), retrievedTrainee.getId());
        assertEquals(trainer.getUserId(), retrievedTrainee.getUserId());
    }

    @Test
    public void getAllTrainers_shouldOk() {

        Collection<Trainer> trainees = trainerService.getAll();
        assertTrue(trainees.isEmpty());

        Trainer trainer1 = new Trainer(1L, List.of(new TrainingType("Gym")), 2L);
        Trainer trainer2 = new Trainer(2L, List.of(new TrainingType("Fitness")), 3L);
        trainerService.save(trainer1);
        trainerService.save(trainer2);

        trainees = trainerService.getAll();

        assertEquals(2, trainees.size());
        assertTrue(trainees.contains(trainer1));
        assertTrue(trainees.contains(trainer2));
    }

    @Test
    public void updateTrainer_shouldOk() {
        Trainer trainer = new Trainer(1L, List.of(new TrainingType("Gym")), 2L);

        trainerService.save(trainer);

        trainer.setUserId(3L);
        Trainer updatedTrainer = trainerService.update(trainer);

        assertEquals(trainer.getId(), updatedTrainer.getId());
        assertEquals(trainer.getUserId(), updatedTrainer.getUserId());
    }

    @Test
    public void deleteTrainee_shouldOk() {
        Trainer trainer = new Trainer(1L, List.of(new TrainingType("Gym")), 2L);

        trainerService.save(trainer);

        trainerService.delete(trainer.getId());

        Optional<Trainer> deletedTrainer = trainerService.get(trainer.getId());
        assertTrue(deletedTrainer.isEmpty());
    }
}

