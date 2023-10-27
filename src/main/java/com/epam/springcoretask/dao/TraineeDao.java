package com.epam.springcoretask.dao;

import com.epam.springcoretask.model.Trainee;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TraineeDao {

    Optional<Trainee> getById(Long id);

    Collection<Trainee> getAll();

    Trainee save(Trainee trainee);

    Trainee update(Trainee trainee);

    void delete(Long id);

    void clearData();

    void initializeData(List<Trainee> traineeData);
}
