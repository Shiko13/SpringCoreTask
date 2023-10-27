package com.epam.springcoretask.dao;

import com.epam.springcoretask.model.TrainingType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TrainingTypeDao {

    Optional<TrainingType> getById(Long id);

    Collection<TrainingType> getAll();

    TrainingType save(TrainingType trainingType);

    void clearData();

    void initializeData(List<TrainingType> trainingTypeData);
}
