package com.epam.springcoretask.model.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TrainingDto {

    private Long id;

    private TraineeDto traineeDto;

    private TrainerDto trainerDto;

    private String name;

    private TrainingTypeDto trainingTypeDto;

    private LocalDate date;

    private Long duration;

}
