package com.epam.springcoretask.service;

import com.epam.springcoretask.dao.TrainerDao;
import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.mapper.TrainerMapper;
import com.epam.springcoretask.mapper.UserMapper;
import com.epam.springcoretask.model.Trainer;
import com.epam.springcoretask.model.User;
import com.epam.springcoretask.model.dto.TrainerDto;
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
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDao trainerDao;

    private final Map<Long, User> userStorage;

    @Override
    public TrainerDto getById(Long id) {
        log.info("Get trainer by id = {}", id);
        Trainer trainer = trainerDao.getById(id).orElseThrow(() ->
                new NotFoundException(String.format("Trainer with id = %d not found", id)));
        return createTrainerDto(trainer);
    }

    @Override
    public Collection<TrainerDto> getAll() {
        log.info("Get all trainers");
        return trainerDao.getAll().stream()
                         .map(this::createTrainerDto)
                         .collect(Collectors.toList());
    }

    @Override
    public TrainerDto save(Trainer trainer) {
        log.info("Save trainer {}", trainer);
        Trainer savedTrainer = trainerDao.save(trainer);
        return createTrainerDto(savedTrainer);
    }

    @Override
    public TrainerDto update(Trainer trainer) {
        log.info("Update trainer {}", trainer);
        Trainer updatedTrainer = trainerDao.update(trainer);
        return createTrainerDto(updatedTrainer);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete trainer by id = {}", id);
        trainerDao.delete(id);
    }

    @Override
    public void clearData() {
        log.info("Clear trainerData");
        trainerDao.clearData();
    }

    @Override
    public void initializeData(List<Trainer> trainerData) {
        log.info("Initialize trainerData");
        trainerDao.initializeData(trainerData);
    }

    private TrainerDto createTrainerDto(Trainer trainer) {
        User user = userStorage.get(trainer.getUserId());
        if (user == null) {
            throw new NotFoundException(String.format("User with id = %d not found", trainer.getUserId()));
        }

        UserDto userDto = UserMapper.toUserDto(user);
        return TrainerMapper.toTrainerDto(trainer, userDto);
    }
}
