package com.epam.springcoretask.dao;

import com.epam.springcoretask.model.TrainingType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TrainingTypeDaoImpl implements TrainingTypeDao {

    private final Map<Long, TrainingType> trainingTypeStorage;
    private static Long idCounter = 1L;
    @Override
    public Optional<TrainingType> getById(Long id) {
        return Optional.ofNullable(trainingTypeStorage.get(id));
    }

    @Override
    public Collection<TrainingType> getAll() {
        return trainingTypeStorage.values();
    }

    @Override
    public TrainingType save(TrainingType trainingType) {
        trainingType.setId(generateUniqueId());
        trainingTypeStorage.put(trainingType.getId(), trainingType);
        return trainingTypeStorage.get(trainingType.getId());
    }

    @Override
    public void clearData() {
        trainingTypeStorage.clear();
    }

    @Override
    public void initializeData(List<TrainingType> trainingTypeData) {
        for (TrainingType trainingType : trainingTypeData) {
            trainingType.setId(generateUniqueId());
            trainingTypeStorage.put(trainingType.getId(), trainingType);
        }
    }

    private Long generateUniqueId() {
        return idCounter++;
    }
}
