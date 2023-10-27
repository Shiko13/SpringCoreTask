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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingDao trainingDao;

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private TrainerDao trainerDao;

    @Mock
    private Map<Long, User> userStorage;
    @Mock
    private Map<Long, TrainingType> trainingTypeStorage;

    @BeforeEach
    void setUp() {
        trainingService = new TrainingServiceImpl(trainingDao, traineeDao, trainerDao, userStorage, trainingTypeStorage);
        trainingTypeStorage.clear();
        userStorage.clear();
    }

    @Test
    void getById_WhenExists_shouldOk() {
        Long trainingId = 1L;
        Training training = createTraining1(trainingId);

        User userTrainee = new User("John", "Doe", true);
        userTrainee.setId(1L);

        User userTrainer = new User("Maria", "Johnson", true);
        userTrainer.setId(2L);

        TraineeDto traineeDto = createTraineeDto(training.getTraineeId(), userTrainee);
        TrainerDto trainerDto = createTrainerDto(training.getTrainerId(), userTrainer);
        TrainingTypeDto trainingTypeDto = createTrainingTypeDto(training.getTypeId());

        Trainee trainee = new Trainee(traineeDto.getDateOfBirth(), traineeDto.getAddress(), traineeDto.getUserDto().getId());
        trainee.setId(1L);
        Trainer trainer = new Trainer(trainerDto.getSpecialization(), trainerDto.getUserDto().getId());
        trainer.setId(2L);

        TrainingType trainingType = new TrainingType("Type 3");
        trainingType.setId(3L);
        trainingTypeStorage.put(trainingType.getId(), trainingType);

        TrainingDto expectedDto = new TrainingDto(training.getId(), traineeDto, trainerDto, training.getName(), trainingTypeDto, training.getDate(), training.getDuration());

        Mockito.when(trainingDao.save(training)).thenReturn(training);
        Mockito.when(trainingDao.getById(training.getId())).thenReturn(Optional.of(training));
        Mockito.when(userStorage.get(1L)).thenReturn(userTrainee);
        Mockito.when(userStorage.get(2L)).thenReturn(userTrainer);
        Mockito.when(traineeDao.getById(1L)).thenReturn(Optional.of(trainee));
        Mockito.when(trainerDao.getById(2L)).thenReturn(Optional.of(trainer));
        Mockito.when(trainingTypeStorage.get(3L)).thenReturn(trainingType);

        trainingService.save(training);
        TrainingDto result = trainingService.getById(trainingId);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(trainingDao).getById(trainingId);
    }

    @Test
    void getById_WhenNotExists_shouldThrowException() {
        Long trainingId = 1L;
        when(trainingDao.getById(trainingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainingService.getById(trainingId));

        verify(trainingDao).getById(trainingId);
    }

    @Test
    void getAll_shouldOk() {
        Long training1Id = 1L;
        Training training1 = createTraining1(training1Id);

        User userTrainee1 = new User("John", "Doe", true);
        userTrainee1.setId(1L);

        User userTrainer1 = new User("Maria", "Johnson", true);
        userTrainer1.setId(2L);

        TraineeDto traineeDto1 = createTraineeDto(training1.getTraineeId(), userTrainee1);
        TrainerDto trainerDto1 = createTrainerDto(training1.getTrainerId(), userTrainer1);
        TrainingTypeDto trainingTypeDto1 = createTrainingTypeDto(training1.getTypeId());

        Trainee trainee1 = new Trainee(traineeDto1.getDateOfBirth(), traineeDto1.getAddress(), traineeDto1.getUserDto().getId());
        trainee1.setId(1L);
        Trainer trainer1 = new Trainer(trainerDto1.getSpecialization(), trainerDto1.getUserDto().getId());
        trainer1.setId(2L);

        TrainingType trainingType1 = new TrainingType("Type 3");
        trainingType1.setId(3L);
        trainingTypeStorage.put(trainingType1.getId(), trainingType1);

        TrainingDto expectedDto1 = new TrainingDto(training1.getId(), traineeDto1, trainerDto1, training1.getName(), trainingTypeDto1, training1.getDate(), training1.getDuration());

        Long training2Id = 2L;
        Training training2 = createTraining2(training2Id);

        User userTrainee2 = new User("James", "Smith", true);
        userTrainee2.setId(3L);

        User userTrainer2 = new User("Olga", "Ray", true);
        userTrainer2.setId(4L);

        TraineeDto traineeDto2 = createTraineeDto(training2.getTraineeId(), userTrainee2);
        TrainerDto trainerDto2 = createTrainerDto(training2.getTrainerId(), userTrainer2);
        TrainingTypeDto trainingTypeDto2 = createTrainingTypeDto(training2.getTypeId());

        Trainee trainee2 = new Trainee(traineeDto2.getDateOfBirth(), traineeDto2.getAddress(), traineeDto2.getUserDto().getId());
        trainee2.setId(3L);
        Trainer trainer2 = new Trainer(trainerDto2.getSpecialization(), trainerDto2.getUserDto().getId());
        trainer2.setId(4L);

        TrainingType trainingType2 = new TrainingType("Type 4");
        trainingType2.setId(4L);
        trainingTypeStorage.put(trainingType2.getId(), trainingType2);

        TrainingDto expectedDto2 = new TrainingDto(training2.getId(), traineeDto2, trainerDto2, training2.getName(), trainingTypeDto2, training2.getDate(), training2.getDuration());

        Mockito.when(trainingDao.save(training1)).thenReturn(training1);
        Mockito.when(userStorage.get(1L)).thenReturn(userTrainee1);
        Mockito.when(userStorage.get(2L)).thenReturn(userTrainer1);
        Mockito.when(traineeDao.getById(1L)).thenReturn(Optional.of(trainee1));
        Mockito.when(trainerDao.getById(2L)).thenReturn(Optional.of(trainer1));
        Mockito.when(trainingTypeStorage.get(3L)).thenReturn(trainingType1);
        Mockito.when(trainingDao.save(training2)).thenReturn(training2);
        Mockito.when(trainingDao.getAll()).thenReturn(List.of(training1, training2));
        Mockito.when(userStorage.get(3L)).thenReturn(userTrainee2);
        Mockito.when(userStorage.get(4L)).thenReturn(userTrainer2);
        Mockito.when(traineeDao.getById(3L)).thenReturn(Optional.of(trainee2));
        Mockito.when(trainerDao.getById(4L)).thenReturn(Optional.of(trainer2));
        Mockito.when(trainingTypeStorage.get(4L)).thenReturn(trainingType2);

        trainingService.save(training1);
        trainingService.save(training2);
        Collection<TrainingDto> result = trainingService.getAll();

        assertNotNull(result);
        assertEquals(List.of(expectedDto1, expectedDto2), result);

        verify(trainingDao).getAll();
    }

    @Test
    void save_shouldOk() {
        Training training = createTraining1(1L);

        User userTrainee = new User("John", "Doe", true);
        userTrainee.setId(1L);

        User userTrainer = new User("Maria", "Johnson", true);
        userTrainer.setId(2L);

        TraineeDto traineeDto = createTraineeDto(training.getTraineeId(), userTrainee);
        TrainerDto trainerDto = createTrainerDto(training.getTrainerId(), userTrainer);
        TrainingTypeDto trainingTypeDto = createTrainingTypeDto(training.getTypeId());
        Trainee trainee = new Trainee(traineeDto.getDateOfBirth(), traineeDto.getAddress(), traineeDto.getUserDto().getId());
        trainee.setId(1L);
        Trainer trainer = new Trainer(trainerDto.getSpecialization(), trainerDto.getUserDto().getId());
        trainer.setId(2L);

        TrainingType trainingType = new TrainingType("Type 3");
        trainingType.setId(3L);
        trainingTypeStorage.put(trainingType.getId(), trainingType);

        TrainingDto expectedDto = new TrainingDto(training.getId(), traineeDto, trainerDto, training.getName(), trainingTypeDto, training.getDate(), training.getDuration());

        Mockito.when(trainingDao.save(training)).thenReturn(training);
        Mockito.when(userStorage.get(1L)).thenReturn(userTrainee);
        Mockito.when(userStorage.get(2L)).thenReturn(userTrainer);
        Mockito.when(traineeDao.getById(1L)).thenReturn(Optional.of(trainee));
        Mockito.when(trainerDao.getById(2L)).thenReturn(Optional.of(trainer));
        Mockito.when(trainingTypeStorage.get(3L)).thenReturn(trainingType);

        TrainingDto result = trainingService.save(training);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(trainingDao).save(training);
    }

    @Test
    void clearData_shouldOk() {
        trainingService.clearData();

        verify(trainingDao).clearData();
    }

    @Test
    void initializeData_shouldOk() {
        List<Training> trainings = createTrainingList();

        trainingService.initializeData(trainings);

        verify(trainingDao).initializeData(trainings);
    }

    @Test
    void save_ifTraineeNotExists_shouldThrowException() {
        Long traineeId = 1L;
        Training training = new Training();
        training.setTraineeId(traineeId);

        when(trainingDao.save(training)).thenReturn(training);

        assertThrows(NotFoundException.class, () -> trainingService.save(training));

        verify(traineeDao).getById(traineeId);
    }

    @Test
    void save_ifTrainerNotExists_shouldThrowException() {
        Long trainerId = 1L;
        Long traineeId = 2L;
        Training training = new Training();
        training.setTrainerId(trainerId);
        training.setTraineeId(traineeId);

        User userTrainee = new User("John", "Doe", true);
        userTrainee.setId(1L);

        TraineeDto traineeDto = createTraineeDto(training.getTraineeId(), userTrainee);
        Trainee trainee = new Trainee(traineeDto.getDateOfBirth(), traineeDto.getAddress(), traineeDto.getUserDto().getId());
        trainee.setUserId(1L);

        when(traineeDao.getById(traineeId)).thenReturn(Optional.of(trainee));
        when(trainingDao.save(training)).thenReturn(training);
        when(userStorage.get(userTrainee.getId())).thenReturn(userTrainee);

        assertThrows(NotFoundException.class, () -> trainingService.save(training));

        verify(trainerDao).getById(trainerId);
    }

    @Test
    void save_ifTrainingTypeNotExists_shouldThrowException() {
        Long trainerId = 1L;
        Long traineeId = 2L;
        Long trainingTypeId = 3L;
        Training training = new Training();
        training.setTrainerId(trainerId);
        training.setTraineeId(traineeId);
        training.setTypeId(trainingTypeId);

        User userTrainee = new User("John", "Doe", true);
        userTrainee.setId(1L);

        User userTrainer = new User("Maria", "Johnson", true);
        userTrainer.setId(2L);

        TraineeDto traineeDto = createTraineeDto(training.getTraineeId(), userTrainee);
        Trainee trainee = new Trainee(traineeDto.getDateOfBirth(), traineeDto.getAddress(), traineeDto.getUserDto().getId());
        trainee.setUserId(1L);

        TrainerDto trainerDto = createTrainerDto(training.getTrainerId(), userTrainer);
        Trainer trainer = new Trainer(trainerDto.getSpecialization(), trainerDto.getUserDto().getId());
        trainer.setId(1L);

        TrainingType trainingType = new TrainingType("Type 3");
        trainingType.setId(3L);
        trainingTypeStorage.put(trainingType.getId(), trainingType);

        when(traineeDao.getById(traineeId)).thenReturn(Optional.of(trainee));
        when(trainerDao.getById(trainerId)).thenReturn(Optional.of(trainer));
        when(trainingDao.save(training)).thenReturn(training);
        when(userStorage.get(userTrainee.getId())).thenReturn(userTrainee);
        when(userStorage.get(userTrainer.getId())).thenReturn(userTrainee);

        assertThrows(NotFoundException.class, () -> trainingService.save(training));
    }

    @Test
    void save_ifUserNotExists_shouldThrowException() {
        Long traineeId = 1L;
        Training training = new Training();
        training.setTraineeId(traineeId);

        User userTrainee = new User("John", "Doe", true);
        userTrainee.setId(1L);

        TraineeDto traineeDto = createTraineeDto(training.getTraineeId(), userTrainee);
        Trainee trainee = new Trainee(traineeDto.getDateOfBirth(), traineeDto.getAddress(), traineeDto.getUserDto().getId());
        trainee.setUserId(1L);

        when(trainingDao.save(training)).thenReturn(training);
        when(traineeDao.getById(traineeId)).thenReturn(Optional.of(trainee));

        assertThrows(NotFoundException.class, () -> trainingService.save(training));
    }

    private Training createTraining1(Long id) {
        Training training = new Training(1L, 2L, "Training Name", 3L, LocalDate.now(), 60L);
        training.setId(id);
        return training;
    }

    private Training createTraining2(Long id) {
        Training training = new Training();
        training.setId(id);
        training.setTraineeId(3L);
        training.setTrainerId(4L);
        training.setTypeId(4L);
        training.setDate(LocalDate.now());
        training.setDuration(45L);
        training.setName("Another Training Name");
        return training;
    }

    private List<Training> createTrainingList() {
        List<Training> trainings = new ArrayList<>();
        for (long i = 1; i <= 2; i++) {
            trainings.add(createTraining1(i));
        }
        return trainings;
    }

    private TraineeDto createTraineeDto(Long traineeId, User userTrainee) {
        Trainee trainee = createTrainee(traineeId);
        UserDto userDto = UserMapper.toUserDto(userTrainee);
        return TraineeMapper.toTraineeDto(trainee, userDto);
    }

    private TrainerDto createTrainerDto(Long trainerId, User userTrainer) {
        Trainer trainer = createTrainer(trainerId);
        UserDto userDto = UserMapper.toUserDto(userTrainer);
        return TrainerMapper.toTrainerDto(trainer, userDto);
    }

    private TrainingTypeDto createTrainingTypeDto(Long typeId) {
        TrainingType trainingType = createTrainingType(typeId);
        return TrainingTypeMapper.toTrainingTypeDto(trainingType);
    }

    private Trainee createTrainee(Long traineeId) {
        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        trainee.setDateOfBirth(LocalDate.of(1990, 1, 15));
        trainee.setAddress("123 Main St");
        trainee.setUserId(traineeId);
        return trainee;
    }

    private Trainer createTrainer(Long trainerId) {
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        trainer.setSpecialization(List.of(new TrainingType("Strength Training"),
                new TrainingType("Cardio")));
        trainer.setUserId(trainerId + 20);
        return trainer;
    }

    private TrainingType createTrainingType(Long typeId) {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(typeId);
        trainingType.setName("Type " + typeId);
        return trainingType;
    }
}


