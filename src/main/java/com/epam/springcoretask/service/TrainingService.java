package com.epam.springcoretask.service;

import com.epam.springcoretask.model.Training;
import com.epam.springcoretask.model.dto.TrainingDto;

import java.util.Collection;
import java.util.List;

public interface TrainingService {

    TrainingDto getById(Long id);

    Collection<TrainingDto> getAll();

    TrainingDto save(Training training);

    void clearData();

    void initializeData(List<Training> trainingData);
}
