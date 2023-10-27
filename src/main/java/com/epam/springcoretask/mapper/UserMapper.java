package com.epam.springcoretask.mapper;

import com.epam.springcoretask.model.User;
import com.epam.springcoretask.model.dto.UserDto;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getIsActive());
    }
}
