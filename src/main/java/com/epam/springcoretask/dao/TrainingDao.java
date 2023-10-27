package com.epam.springcoretask.dao;

import com.epam.springcoretask.model.Training;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TrainingDao {

    Optional<Training> getById(Long id);

    Collection<Training> getAll();

    Training save(Training training);

    void clearData();

    void initializeData(List<Training> trainingData);
}
