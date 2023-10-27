package com.epam.springcoretask.dao;

import com.epam.springcoretask.model.Trainer;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TrainerDao {

    Optional<Trainer> getById(Long id);

    Collection<Trainer> getAll();

    Trainer save(Trainer trainer);

    Trainer update(Trainer trainer);

    void delete(Long id);

    void clearData();

    void initializeData(List<Trainer> trainerData);
}

