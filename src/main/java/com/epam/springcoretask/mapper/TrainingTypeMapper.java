package com.epam.springcoretask.mapper;

import com.epam.springcoretask.model.TrainingType;
import com.epam.springcoretask.model.dto.TrainingTypeDto;

public class TrainingTypeMapper {

    public static TrainingTypeDto toTrainingTypeDto(TrainingType trainingType) {
        return new TrainingTypeDto(trainingType.getId(),
                trainingType.getName());
    }
}
