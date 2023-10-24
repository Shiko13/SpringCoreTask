package com.epam.springcoretask.service;

import com.epam.springcoretask.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeService {

    Optional<Trainee> get(Long id);

    List<Trainee> getAll();

    void save(Trainee trainee);

    void update(Trainee trainee);

    void delete(Trainee trainee);
}
