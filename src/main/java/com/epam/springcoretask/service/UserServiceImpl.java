package com.epam.springcoretask.service;

import com.epam.springcoretask.dao.UserDao;
import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.mapper.UserMapper;
import com.epam.springcoretask.model.User;
import com.epam.springcoretask.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, DataService<User> {

    private final UserDao userDao;

    private final static String PATH = "data/user-data.json";

    @Override
    public UserDto getById(Long id) {
        log.info("Get user by id = {}", id);
        User user = userDao.getById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id = %d not found", id)));
        return UserMapper.toUserDto(user);
    }

    @Override
    public Collection<UserDto> getAll() {
        log.info("Get all users");
        return userDao.getAll().stream()
                          .map(UserMapper::toUserDto)
                          .collect(Collectors.toList());
    }

    @Override
    public UserDto save(User user) {
        log.info("Save user {}", user);
        User savedUser = userDao.save(user);
        return UserMapper.toUserDto(savedUser);
    }

    @Override
    public void clearData() {
        log.info("Clear userData");
        userDao.clearData();
    }

    @Override
    public void initializeData(List<User> userData) {
        log.info("Initialize userData");
        userDao.initializeData(userData);
    }

    @Override
    public Class<User> getDataType() {
        return User.class;
    }

    @Override
    public String getDataPath() {
        return PATH;
    }
}
