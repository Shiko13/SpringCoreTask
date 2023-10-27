package com.epam.springcoretask.mapper;

import com.epam.springcoretask.model.Trainer;
import com.epam.springcoretask.model.dto.TrainerDto;
import com.epam.springcoretask.model.dto.UserDto;

public class TrainerMapper {

    public static TrainerDto toTrainerDto(Trainer trainer, UserDto userDto) {
        return new TrainerDto(trainer.getId(),
                trainer.getSpecialization(),
                userDto);
    }
}
