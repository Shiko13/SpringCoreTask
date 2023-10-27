package com.epam.springcoretask.service;

import com.epam.springcoretask.dao.TrainingTypeDao;
import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.mapper.TrainingTypeMapper;
import com.epam.springcoretask.model.TrainingType;
import com.epam.springcoretask.model.dto.TrainingTypeDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeDao trainingTypeDao;

    @Override
    public TrainingTypeDto getById(Long id) {
        log.info("Get trainingType by id = {}", id);
        TrainingType trainingType = trainingTypeDao.getById(id).orElseThrow(() ->
                new NotFoundException(String.format("Training type with id = %d not found", id)));
        return TrainingTypeMapper.toTrainingTypeDto(trainingType);
    }

    @Override
    public Collection<TrainingTypeDto> getAll() {
        log.info("Get all training types");
        return trainingTypeDao.getAll().stream()
                      .map(TrainingTypeMapper::toTrainingTypeDto)
                      .collect(Collectors.toList());
    }

    @Override
    public TrainingTypeDto save(TrainingType trainingType) {
        log.info("Save training type {}", trainingType);
        TrainingType savedTrainingType = trainingTypeDao.save(trainingType);
        return TrainingTypeMapper.toTrainingTypeDto(savedTrainingType);
    }

    @Override
    public void clearData() {
        log.info("Clear trainingTypeData");
        trainingTypeDao.clearData();
    }

    @Override
    public void initializeData(List<TrainingType> trainingTypeData) {
        log.info("Initialize trainingTypeData");
        trainingTypeDao.initializeData(trainingTypeData);
    }
}
