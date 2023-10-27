package com.epam.springcoretask.mapper;

import com.epam.springcoretask.model.Trainee;
import com.epam.springcoretask.model.dto.TraineeDto;
import com.epam.springcoretask.model.dto.UserDto;

public class TraineeMapper {

    public static TraineeDto toTraineeDto(Trainee trainee, UserDto userDto) {
        return new TraineeDto(trainee.getId(),
                trainee.getDateOfBirth(),
                trainee.getAddress(),
                userDto);
    }
}
