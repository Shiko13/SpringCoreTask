package com.epam.springcoretask.service;

import com.epam.springcoretask.model.User;
import com.epam.springcoretask.model.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {

    UserDto getById(Long id);

    Collection<UserDto> getAll();

    UserDto save(User user);

    void clearData();

    void initializeData(List<User> userData);
}
