package com.epam.springcoretask.dao;

import com.epam.springcoretask.model.Training;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TrainingDaoImpl implements TrainingDao {

    private final Map<Long, Training> trainingStorage;

    private static Long idCounter = 1L;

    @Override
    public Optional<Training> getById(Long id) {
        return Optional.ofNullable(trainingStorage.get(id));
    }

    @Override
    public Collection<Training> getAll() {
        return trainingStorage.values();
    }

    @Override
    public Training save(Training training) {
        training.setId(generateUniqueId());
        trainingStorage.put(training.getId(), training);
        return trainingStorage.get(training.getId());
    }

    @Override
    public void clearData() {
        trainingStorage.clear();
    }

    @Override
    public void initializeData(List<Training> trainingData) {
        for (Training training : trainingData) {
            training.setId(generateUniqueId());
            trainingStorage.put(training.getId(), training);
        }
    }

    private Long generateUniqueId() {
        return idCounter++;
    }
}
