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
public class TraineeDto {

    private Long id;

    private LocalDate dateOfBirth;

    private String address;

    private UserDto userDto;

}
