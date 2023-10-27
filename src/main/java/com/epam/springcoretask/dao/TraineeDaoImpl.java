package com.epam.springcoretask.dao;

import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.model.Trainee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TraineeDaoImpl implements TraineeDao {

    private final Map<Long, Trainee> traineeStorage;
    private static Long idCounter = 1L;

    @Override
    public Optional<Trainee> getById(Long id) {
        return Optional.ofNullable(traineeStorage.get(id));
    }

    @Override
    public Collection<Trainee> getAll() {
        return traineeStorage.values();
    }

    @Override
    public Trainee save(Trainee trainee) {
        trainee.setId(generateUniqueId());
        traineeStorage.put(trainee.getId(), trainee);
        return traineeStorage.get(trainee.getId());
    }

    @Override
    public Trainee update(Trainee trainee) {
        Long id = trainee.getId();
        if (id == null) {
            throw new IllegalStateException("Trainee ID must be set for an update.");
        }

        traineeStorage.put(trainee.getId(), trainee);
        return traineeStorage.put(trainee.getId(), trainee);
    }

    @Override
    public void delete(Long id) {
        if (traineeStorage.get(id) == null) {
            throw new NotFoundException(String.format("Trainee with id = %d not found", id));
        }
        traineeStorage.remove(id);
    }

    @Override
    public void clearData() {
        traineeStorage.clear();
    }

    @Override
    public void initializeData(List<Trainee> traineeData) {
        for (Trainee trainee : traineeData) {
            trainee.setId(generateUniqueId());
            traineeStorage.put(trainee.getId(), trainee);
        }
    }

    private Long generateUniqueId() {
        return idCounter++;
    }
}
