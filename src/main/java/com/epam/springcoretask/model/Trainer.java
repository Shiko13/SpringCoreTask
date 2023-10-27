package com.epam.springcoretask.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Trainer {

    private Long id;

    private List<TrainingType> specialization;

    private Long userId;

    public Trainer(List<TrainingType> specialization, Long userId) {
        this.specialization = specialization;
        this.userId = userId;
    }
}
