package com.epam.springcoretask.model.dto;

import com.epam.springcoretask.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TrainerDto {

    private Long id;

    private List<TrainingType> specialization;

    private UserDto userDto;

}
