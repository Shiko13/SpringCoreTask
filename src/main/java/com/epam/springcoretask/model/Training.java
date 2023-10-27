package com.epam.springcoretask.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Training {

    private Long id;

    private Long traineeId;

    private Long trainerId;

    private String name;

    private Long typeId;

    private LocalDate date;

    private Long duration;

    public Training(Long traineeId, Long trainerId, String name, Long typeId, LocalDate date, Long duration) {
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.name = name;
        this.typeId = typeId;
        this.date = date;
        this.duration = duration;
    }
}
