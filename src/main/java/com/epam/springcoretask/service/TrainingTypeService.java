package com.epam.springcoretask.service;

import com.epam.springcoretask.model.TrainingType;
import com.epam.springcoretask.model.dto.TrainingTypeDto;

import java.util.Collection;
import java.util.List;

public interface TrainingTypeService {

    TrainingTypeDto getById(Long id);

    Collection<TrainingTypeDto> getAll();

    TrainingTypeDto save(TrainingType trainingType);

    void clearData();

    void initializeData(List<TrainingType> trainingTypeData);
}
