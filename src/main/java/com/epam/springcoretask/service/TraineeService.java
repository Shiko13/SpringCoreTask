package com.epam.springcoretask.service;

import com.epam.springcoretask.model.Trainee;
import com.epam.springcoretask.model.dto.TraineeDto;

import java.util.Collection;
import java.util.List;

public interface TraineeService {

    TraineeDto getById(Long id);

    Collection<TraineeDto> getAll();

    TraineeDto save(Trainee trainee);

    TraineeDto update(Trainee trainee);

    void delete(Long id);

    void clearData();

    void initializeData(List<Trainee> traineeData);
}
