package com.epam.springcoretask.service;

import com.epam.springcoretask.dao.TraineeDao;
import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.mapper.TraineeMapper;
import com.epam.springcoretask.mapper.UserMapper;
import com.epam.springcoretask.model.Trainee;
import com.epam.springcoretask.model.User;
import com.epam.springcoretask.model.dto.TraineeDto;
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
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDao traineeDao;

    private final Map<Long, User> userStorage;

    @Override
    public TraineeDto getById(Long id) {
        log.info("Get trainee by id = {}", id);
        Trainee trainee = traineeDao.getById(id).orElseThrow(() ->
                new NotFoundException(String.format("Trainee with id = %d not found", id)));
        return createTraineeDto(trainee);
    }

    @Override
    public Collection<TraineeDto> getAll() {
        log.info("Get all trainees");
        return traineeDao.getAll().stream()
                         .map(this::createTraineeDto)
                         .collect(Collectors.toList());
    }

    @Override
    public TraineeDto save(Trainee trainee) {
        log.info("Save trainee {}", trainee);
        Trainee savedTrainee = traineeDao.save(trainee);
        return createTraineeDto(savedTrainee);
    }

    @Override
    public TraineeDto update(Trainee trainee) {
        log.info("Update trainee {}", trainee);
        Trainee updatedTrainee = traineeDao.update(trainee);
        return createTraineeDto(updatedTrainee);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete trainee by id = {}", id);
        traineeDao.delete(id);
    }

    @Override
    public void clearData() {
        log.info("Clear traineeData");
        traineeDao.clearData();
    }

    @Override
    public void initializeData(List<Trainee> traineeData) {
        log.info("Initialize traineeData");
        traineeDao.initializeData(traineeData);
    }

    private TraineeDto createTraineeDto(Trainee trainee) {
        User user = userStorage.get(trainee.getUserId());
        if (user == null) {
            throw new NotFoundException(String.format("User with id = %d not found", trainee.getUserId()));
        }

        UserDto userDto = UserMapper.toUserDto(user);
        return TraineeMapper.toTraineeDto(trainee, userDto);
    }
}
