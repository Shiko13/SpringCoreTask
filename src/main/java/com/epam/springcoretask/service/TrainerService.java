package com.epam.springcoretask.service;

import com.epam.springcoretask.model.Trainer;
import com.epam.springcoretask.model.dto.TrainerDto;

import java.util.Collection;
import java.util.List;

public interface TrainerService {

    TrainerDto getById(Long id);

    Collection<TrainerDto> getAll();

    TrainerDto save(Trainer trainer);

    TrainerDto update(Trainer trainer);

    void delete(Long id);

    void clearData();

    void initializeData(List<Trainer> trainerData);
}
