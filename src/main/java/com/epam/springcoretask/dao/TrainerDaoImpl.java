package com.epam.springcoretask.dao;

import com.epam.springcoretask.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainerDaoImpl implements TrainerDao {

    private final Map<Long, Trainer> trainerStorage;

    private static Long idCounter = 1L;

    @Autowired
    public TrainerDaoImpl(Map<Long, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }


    @Override
    public Optional<Trainer> getById(Long id) {
        return Optional.ofNullable(trainerStorage.get(id));
    }

    @Override
    public Collection<Trainer> getAll() {
        return trainerStorage.values();
    }

    @Override
    public Trainer save(Trainer trainer) {
        trainer.setId(generateUniqueId());
        trainerStorage.put(trainer.getId(), trainer);
        return trainerStorage.get(trainer.getId());
    }

    @Override
    public Trainer update(Trainer trainer) {
        Long id = trainer.getId();
        if (id == null) {
            throw new IllegalStateException("Trainer ID must be set for an update.");
        }

        trainerStorage.put(trainer.getId(), trainer);
        return trainerStorage.put(trainer.getId(), trainer);
    }

    @Override
    public void delete(Long id) {
        trainerStorage.remove(id);
    }

    @Override
    public void clearData() {
        trainerStorage.clear();
    }

    @Override
    public void initializeData(List<Trainer> trainerData) {
        for (Trainer trainer : trainerData) {
            trainer.setId(generateUniqueId());
            trainerStorage.put(trainer.getId(), trainer);
        }
    }

    private Long generateUniqueId() {
        return idCounter++;
    }
}
