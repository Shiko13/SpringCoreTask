package com.epam.springcoretask.dao;

import com.epam.springcoretask.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getById(Long id);

    Collection<User> getAll();

    User save(User user);

    void clearData();

    void initializeData(List<User> userData);
}
