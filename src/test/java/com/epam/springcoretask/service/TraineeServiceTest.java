package com.epam.springcoretask.service;

import com.epam.springcoretask.dao.TraineeDao;
import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.mapper.TraineeMapper;
import com.epam.springcoretask.mapper.UserMapper;
import com.epam.springcoretask.model.Trainee;
import com.epam.springcoretask.model.User;
import com.epam.springcoretask.model.dto.TraineeDto;
import com.epam.springcoretask.model.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {

    @Mock
    private TraineeDao traineeDao;
    @Mock
    private Map<Long, User> userStorage;
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        traineeService = new TraineeServiceImpl(traineeDao, userStorage);
    }

    @Test
    void getById_shouldOk() {
        Long userId = 1L;
        Long traineeId = 1L;

        User user = new User("John", "Doe", true);
        user.setId(userId);

        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        trainee.setUserId(userId);

        Mockito.when(traineeDao.getById(traineeId)).thenReturn(Optional.of(trainee));
        Mockito.when(userStorage.get(traineeId)).thenReturn(user);

        UserDto expectedUserDto = UserMapper.toUserDto(user);
        TraineeDto expectedTraineeDto = TraineeMapper.toTraineeDto(trainee, expectedUserDto);

        TraineeDto result = traineeService.getById(traineeId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedUserDto.getFirstName(), result.getUserDto().getFirstName());
        Assertions.assertEquals(expectedUserDto.getLastName(), result.getUserDto().getLastName());
        Assertions.assertEquals(expectedUserDto.getIsActive(), result.getUserDto().getIsActive());

        Assertions.assertEquals(expectedTraineeDto.getId(), result.getId());
    }

    @Test
    void testGetById_WhenTraineeDoesNotExist() {
        Long traineeId = 1L;

        Mockito.when(traineeDao.getById(traineeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> traineeService.getById(traineeId));
    }

    @Test
    void getAll_shouldOk() {
        List<Trainee> trainees = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<TraineeDto> expectedTraineeDtos = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Trainee trainee = new Trainee();
            trainee.setId((long) i);
            trainee.setDateOfBirth(LocalDate.of(1990, 1, 15));
            trainee.setAddress("123 Main St");
            trainee.setUserId((long) i);

            User user = new User("User" + i, "Doe" + i, true);
            user.setId((long) i);
            UserDto userDto = UserMapper.toUserDto(user);

            trainees.add(trainee);
            users.add(user);

            expectedTraineeDtos.add(TraineeMapper.toTraineeDto(trainee, userDto));
        }

        Mockito.when(traineeDao.getAll()).thenReturn(trainees);

        for (int i = 1; i <= 5; i++) {
            Mockito.when(userStorage.get((long) i)).thenReturn(users.get(i - 1));
        }

        Collection<TraineeDto> result = traineeService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedTraineeDtos.size(), result.size());

        for (TraineeDto expectedDto : expectedTraineeDtos) {
            TraineeDto actualDto =
                    result.stream().filter(dto -> dto.getId().equals(expectedDto.getId())).findFirst().orElse(null);

            Assertions.assertNotNull(actualDto);

            Assertions.assertEquals(expectedDto.getUserDto().getFirstName(), actualDto.getUserDto().getFirstName());
            Assertions.assertEquals(expectedDto.getUserDto().getLastName(), actualDto.getUserDto().getLastName());
            Assertions.assertEquals(expectedDto.getUserDto().getIsActive(), actualDto.getUserDto().getIsActive());
            Assertions.assertEquals(expectedDto.getDateOfBirth(), actualDto.getDateOfBirth());
            Assertions.assertEquals(expectedDto.getAddress(), actualDto.getAddress());
        }
    }

    @Test
    void save_shouldOk() {
        User user = new User("John", "Doe", true);
        user.setId(1L);

        Trainee traineeToSave = new Trainee("123 Main St", user.getId());

        traineeToSave.setUserId(user.getId());

        Mockito.when(traineeDao.save(traineeToSave)).thenReturn(traineeToSave);
        Mockito.when(userStorage.get(1L)).thenReturn(user);

        TraineeDto result = traineeService.save(traineeToSave);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(traineeToSave.getDateOfBirth(), result.getDateOfBirth());
        Assertions.assertEquals(traineeToSave.getAddress(), result.getAddress());
        Assertions.assertEquals(user.getId(), result.getUserDto().getId());
        Assertions.assertEquals(user.getFirstName(), result.getUserDto().getFirstName());
        Assertions.assertEquals(user.getLastName(), result.getUserDto().getLastName());
        Assertions.assertEquals(user.getIsActive(), result.getUserDto().getIsActive());
    }


    @Test
    void update_shouldOk() {
        User user = new User("Jane", "Smith", true);
        user.setId(1L);

        Trainee traineeToUpdate = new Trainee(LocalDate.of(1985, 6, 10), user.getId());
        traineeToUpdate.setId(1L);

        traineeToUpdate.setUserId(user.getId());

        Mockito.when(traineeDao.update(traineeToUpdate)).thenReturn(traineeToUpdate);
        Mockito.when(userStorage.get(1L)).thenReturn(user);

        TraineeDto result = traineeService.update(traineeToUpdate);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(traineeToUpdate.getDateOfBirth(), result.getDateOfBirth());
        Assertions.assertEquals(traineeToUpdate.getAddress(), result.getAddress());
        Assertions.assertEquals(user.getId(), result.getUserDto().getId());
        Assertions.assertEquals(user.getFirstName(), result.getUserDto().getFirstName());
        Assertions.assertEquals(user.getLastName(), result.getUserDto().getLastName());
        Assertions.assertEquals(user.getIsActive(), result.getUserDto().getIsActive());
    }

    @Test
    void delete_shouldOk() {
        Long traineeId = 1L;

        Mockito.doNothing().when(traineeDao).delete(traineeId);

        traineeService.delete(traineeId);
    }

    @Test
    void clearData_shouldOk() {
        Mockito.doNothing().when(traineeDao).clearData();

        Assertions.assertDoesNotThrow(() -> traineeService.clearData());
    }

    @Test
    void initializeData_shouldOk() {
        List<Trainee> traineeData = new ArrayList<>();
        traineeData.add(new Trainee( 1L ));
        traineeData.add(new Trainee(2L));
        traineeData.add(new Trainee(3L));

        Mockito.doNothing().when(traineeDao).initializeData(traineeData);

        traineeService.initializeData(traineeData);
    }

    @Test
    void createWhenUserIsNull_shouldThrowException() {
        Long traineeId = 1L;
        Trainee trainee = new Trainee( LocalDate.of(1990, 1, 15), "123 Main St", 42L);
        trainee.setId(traineeId);

        Mockito.when(traineeDao.save(trainee)).thenReturn(trainee);
        Mockito.when(userStorage.get(42L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> traineeService.save(trainee));
    }
}

