package com.epam.springcoretask.service;

import com.epam.springcoretask.dao.TrainingTypeDao;
import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.mapper.TrainingTypeMapper;
import com.epam.springcoretask.model.TrainingType;
import com.epam.springcoretask.model.dto.TrainingTypeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceTest {

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @Mock
    private TrainingTypeDao trainingTypeDao;

    @Test
    void getById_WhenExists_shouldReturnDto() {
        Long typeId = 1L;
        TrainingType trainingType = new TrainingType("Type 1");
        trainingType.setId(typeId);

        when(trainingTypeDao.getById(typeId)).thenReturn(Optional.of(trainingType));

        TrainingTypeDto result = trainingTypeService.getById(typeId);

        assertNotNull(result);
        assertEquals(typeId, result.getId());
        assertEquals("Type 1", result.getName());

        verify(trainingTypeDao).getById(typeId);
    }

    @Test
    void getById_WhenTrainingTypeDoesNotExist_shouldThrowException() {
        Long typeId = 1L;

        when(trainingTypeDao.getById(typeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainingTypeService.getById(typeId));

        verify(trainingTypeDao).getById(typeId);
    }

    @Test
    void getAll_shouldReturnListOfDto() {
        TrainingType trainingType1 = new TrainingType("Type 1");
        trainingType1.setId(1L);
        TrainingType trainingType2 = new TrainingType("Type 2");
        trainingType1.setId(2L);
        List<TrainingType> trainingTypes = List.of(
                trainingType1, trainingType2
        );

        when(trainingTypeDao.getAll()).thenReturn(trainingTypes);

        Collection<TrainingTypeDto> result = trainingTypeService.getAll();

        assertNotNull(result);
        assertEquals(trainingTypes.size(), result.size());

        List<TrainingTypeDto> expectedDtos = trainingTypes.stream()
                                                          .map(TrainingTypeMapper::toTrainingTypeDto)
                                                          .toList();

        assertTrue(result.containsAll(expectedDtos));

        verify(trainingTypeDao).getAll();
    }

    @Test
    void save_shouldReturnSavedDto() {
        TrainingType trainingType = new TrainingType("Type 1");
        trainingType.setId(1L);

        when(trainingTypeDao.save(trainingType)).thenReturn(trainingType);

        TrainingTypeDto result = trainingTypeService.save(trainingType);

        assertNotNull(result);
        assertEquals(trainingType.getId(), result.getId());
        assertEquals(trainingType.getName(), result.getName());

        verify(trainingTypeDao).save(trainingType);
    }

    @Test
    void clearData_shouldOk() {
        Mockito.doNothing().when(trainingTypeDao).clearData();

        Assertions.assertDoesNotThrow(() -> trainingTypeService.clearData());
    }
}
