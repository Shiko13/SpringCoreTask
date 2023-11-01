package com.epam.springcoretask.service;

import com.epam.springcoretask.dao.TraineeDao;
import com.epam.springcoretask.dao.TrainerDao;
import com.epam.springcoretask.dao.TrainingDao;
import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.mapper.TraineeMapper;
import com.epam.springcoretask.mapper.TrainerMapper;
import com.epam.springcoretask.mapper.TrainingTypeMapper;
import com.epam.springcoretask.mapper.UserMapper;
import com.epam.springcoretask.model.Trainee;
import com.epam.springcoretask.model.Trainer;
import com.epam.springcoretask.model.Training;
import com.epam.springcoretask.model.TrainingType;
import com.epam.springcoretask.model.User;
import com.epam.springcoretask.model.dto.TraineeDto;
import com.epam.springcoretask.model.dto.TrainerDto;
import com.epam.springcoretask.model.dto.TrainingDto;
import com.epam.springcoretask.model.dto.TrainingTypeDto;
import com.epam.springcoretask.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TrainingServiceImpl implements TrainingService, DataService<Training> {

    private final TrainingDao trainingDao;

    private final TraineeDao traineeDao;

    private final TrainerDao trainerDao;

    private final Map<Long, User> userStorage;

    private final Map<Long, TrainingType> trainingTypeStorage;
    private final static String PATH = "data/training-data.json";

    @Override
    public TrainingDto getById(Long id) {
        log.info("Get training by id = {}", id);
        Training training = trainingDao.getById(id).orElseThrow(() ->
                new NotFoundException(String.format("Training with id = %d not found", id)));
        return createTrainingDto(training);
    }

    @Override
    public Collection<TrainingDto> getAll() {
        log.info("Get all trainings");
        return trainingDao.getAll().stream()
                         .map(this::createTrainingDto)
                         .collect(Collectors.toList());
    }

    @Override
    public TrainingDto save(Training training) {
        log.info("Save training {}", training);
        Training savedTraining = trainingDao.save(training);
        return createTrainingDto(savedTraining);
    }

    @Override
    public void clearData() {
        log.info("Clear trainingData");
        trainingDao.clearData();
    }

    @Override
    public void initializeData(List<Training> trainingData) {
        log.info("Initialize trainingData");
        trainingDao.initializeData(trainingData);
    }

    @Override
    public Class<Training> getDataType() {
        return Training.class;
    }

    @Override
    public String getDataPath() {
        return PATH;
    }

    private TrainingDto createTrainingDto(Training training) {
        TraineeDto traineeDto = getTraineeDto(training.getTraineeId());
        TrainerDto trainerDto = getTrainerDto(training.getTrainerId());
        TrainingTypeDto trainingTypeDto = getTrainingTypeDto(training.getTypeId());

        return new TrainingDto(training.getId(), traineeDto, trainerDto, training.getName(), trainingTypeDto, training.getDate(), training.getDuration());
    }

    private TraineeDto getTraineeDto(Long traineeId) {
        Trainee trainee = traineeDao.getById(traineeId).orElseThrow(() ->
                new NotFoundException(String.format("Trainee with id = %d not found", traineeId)));
        User traineeUser = getUserById(trainee.getUserId());
        UserDto traineeUserDto = UserMapper.toUserDto(traineeUser);

        return TraineeMapper.toTraineeDto(trainee, traineeUserDto);
    }

    private TrainerDto getTrainerDto(Long trainerId) {
        Trainer trainer = trainerDao.getById(trainerId).orElseThrow(() ->
                new NotFoundException(String.format("Trainer with id = %d not found", trainerId)));
        User trainerUser = getUserById(trainer.getUserId());
        UserDto trainerUserDto = UserMapper.toUserDto(trainerUser);

        return TrainerMapper.toTrainerDto(trainer, trainerUserDto);
    }

    private TrainingTypeDto getTrainingTypeDto(Long typeId) {
        TrainingType trainingType = trainingTypeStorage.get(typeId);
        if (trainingType == null) {
            throw new NotFoundException(String.format("Training type with id = %d not found", typeId));
        }

        return TrainingTypeMapper.toTrainingTypeDto(trainingType);
    }

    private User getUserById(Long userId) {
        User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException(String.format("User with id = %d not found", userId));
        }
        return user;
    }
}
