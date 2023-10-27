package com.epam.springcoretask.service;

import com.epam.springcoretask.dao.TrainerDao;
import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.mapper.TrainerMapper;
import com.epam.springcoretask.mapper.UserMapper;
import com.epam.springcoretask.model.Trainer;
import com.epam.springcoretask.model.TrainingType;
import com.epam.springcoretask.model.User;
import com.epam.springcoretask.model.dto.TrainerDto;
import com.epam.springcoretask.model.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

    @Mock
    private TrainerDao trainerDao;
    @Mock
    private Map<Long, User> userStorage;
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        trainerService = new TrainerServiceImpl(trainerDao, userStorage);
    }

    @Test
    void getById_shouldOk() {
        Long userId = 1L;
        Long trainerId = 1L;

        User user = new User("John", "Doe", true);
        user.setId(userId);

        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        trainer.setUserId(userId);

        Mockito.when(trainerDao.getById(trainerId)).thenReturn(Optional.of(trainer));
        Mockito.when(userStorage.get(trainerId)).thenReturn(user);

        UserDto expectedUserDto = UserMapper.toUserDto(user);
        TrainerDto expectedTrainerDto = TrainerMapper.toTrainerDto(trainer, expectedUserDto);

        TrainerDto result = trainerService.getById(trainerId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedUserDto.getFirstName(), result.getUserDto().getFirstName());
        Assertions.assertEquals(expectedUserDto.getLastName(), result.getUserDto().getLastName());
        Assertions.assertEquals(expectedUserDto.getIsActive(), result.getUserDto().getIsActive());

        Assertions.assertEquals(expectedTrainerDto.getId(), result.getId());
    }

    @Test
    void testGetById_WhenTrainerDoesNotExist() {

        Long trainerId = 1L;

        Mockito.when(trainerDao.getById(trainerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainerService.getById(trainerId));
    }

    @Test
    void getAll_shouldOk() {
        List<Trainer> trainers = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<TrainerDto> expectedTrainerDtos = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Trainer trainer = new Trainer();
            trainer.setId((long) i);
            trainer.setSpecialization(List.of(new TrainingType("CrossFit"),
                    new TrainingType("Cardio")));
            trainer.setUserId((long) i);

            User user = new User("User" + i, "Doe" + i, true);
            user.setId((long) i);
            UserDto userDto = UserMapper.toUserDto(user);

            trainers.add(trainer);
            users.add(user);

            expectedTrainerDtos.add(TrainerMapper.toTrainerDto(trainer, userDto));
        }

        Mockito.when(trainerDao.getAll()).thenReturn(trainers);

        for (int i = 1; i <= 5; i++) {
            Mockito.when(userStorage.get((long) i)).thenReturn(users.get(i - 1));
        }

        Collection<TrainerDto> result = trainerService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedTrainerDtos.size(), result.size());

        for (TrainerDto expectedDto : expectedTrainerDtos) {
            TrainerDto actualDto =
                    result.stream().filter(dto -> dto.getId().equals(expectedDto.getId())).findFirst().orElse(null);

            Assertions.assertNotNull(actualDto);

            Assertions.assertEquals(expectedDto.getUserDto().getFirstName(), actualDto.getUserDto().getFirstName());
            Assertions.assertEquals(expectedDto.getUserDto().getLastName(), actualDto.getUserDto().getLastName());
            Assertions.assertEquals(expectedDto.getUserDto().getIsActive(), actualDto.getUserDto().getIsActive());
            Assertions.assertEquals(expectedDto.getSpecialization(), actualDto.getSpecialization());
        }
    }

    @Test
    void save_shouldOk() {
        Trainer trainerToSave = new Trainer();
        trainerToSave.setSpecialization(List.of(new TrainingType("CrossFit"),
                new TrainingType("Cardio")));

        User user = new User("John", "Doe", true);
        user.setId(1L);
        trainerToSave.setUserId(user.getId());

        Mockito.when(trainerDao.save(trainerToSave)).thenReturn(trainerToSave);
        Mockito.when(userStorage.get(1L)).thenReturn(user);

        TrainerDto result = trainerService.save(trainerToSave);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(trainerToSave.getSpecialization(), result.getSpecialization());
        Assertions.assertEquals(user.getId(), result.getUserDto().getId());
        Assertions.assertEquals(user.getFirstName(), result.getUserDto().getFirstName());
        Assertions.assertEquals(user.getLastName(), result.getUserDto().getLastName());
        Assertions.assertEquals(user.getIsActive(), result.getUserDto().getIsActive());
    }

    @Test
    void update_shouldOk() {
        Trainer trainerToUpdate = new Trainer();
        trainerToUpdate.setId(1L);
        trainerToUpdate.setSpecialization(List.of(new TrainingType("CrossFit"),
                new TrainingType("Cardio")));

        User user = new User("Jane", "Smith", true);
        user.setId(1L);
        trainerToUpdate.setUserId(user.getId());

        Mockito.when(trainerDao.update(trainerToUpdate)).thenReturn(trainerToUpdate);
        Mockito.when(userStorage.get(1L)).thenReturn(user);

        TrainerDto result = trainerService.update(trainerToUpdate);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(trainerToUpdate.getSpecialization(), result.getSpecialization());
        Assertions.assertEquals(user.getId(), result.getUserDto().getId());
        Assertions.assertEquals(user.getFirstName(), result.getUserDto().getFirstName());
        Assertions.assertEquals(user.getLastName(), result.getUserDto().getLastName());
        Assertions.assertEquals(user.getIsActive(), result.getUserDto().getIsActive());
    }

    @Test
    void delete_shouldOk() {
        Long trainerId = 1L;

        Mockito.doNothing().when(trainerDao).delete(trainerId);

        trainerService.delete(trainerId);
    }

    @Test
    void clearData_shouldOk() {

        Mockito.doNothing().when(trainerDao).clearData();

        Assertions.assertDoesNotThrow(() -> trainerService.clearData());
    }

    @Test
    void initializeData_shouldOk() {
        List<Trainer> trainerData = new ArrayList<>();
        trainerData.add(new Trainer());
        trainerData.add(new Trainer());
        trainerData.add(new Trainer());

        Mockito.doNothing().when(trainerDao).initializeData(trainerData);

        trainerService.initializeData(trainerData);
    }

    @Test
    void createWhenUserIsNull_shouldThrowException() {
        Long trainerId = 1L;
        Trainer trainer = new Trainer( List.of(new TrainingType("Yoga")), 42L);
        trainer.setId(trainerId);

        Mockito.when(trainerDao.save(trainer)).thenReturn(trainer);
        Mockito.when(userStorage.get(42L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> trainerService.save(trainer));
    }
}