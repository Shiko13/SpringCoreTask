package com.epam.springcoretask.dao;

import com.epam.springcoretask.model.User;
import com.epam.springcoretask.util.RandomStringGenerator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private final Map<Long, User> userStorage;
    private static Long idCounter = 1L;
    private static Long usernameSerialNumberCounter = 1L;

    @Value("${password.length}")
    private final int passwordLength = 10;

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userStorage.get(id));
    }

    @Override
    public Collection<User> getAll() {
        return userStorage.values();
    }

    @Override
    public User save(User user) {
        User entireUser = generateEntireUser(user);
        userStorage.put(user.getId(), entireUser);
        return userStorage.get(user.getId());
    }

    @Override
    public void clearData() {
        userStorage.clear();
    }

    @Override
    public void initializeData(List<User> userData) {
        for (User user : userData) {
            User allUser = generateEntireUser(user);
            userStorage.put(user.getId(), allUser);
        }
    }

    public String generateUserName(User user) {
        String userName = user.getFirstName() + "." + user.getLastName();
        boolean isAvailable = userStorage.values()
                                         .stream()
                                         .noneMatch(u -> u.getUserName() != null &&
                                                 u.getUserName().equals(userName));
        if (isAvailable) {
            return userName;
        } else {
            Long serialNumber = generateUsernameSerialNumber();
            return userName + "-" + serialNumber;
        }
    }

    private User generateEntireUser(User user) {
        Long id = generateUniqueId();
        user.setId(id);

        String userName = generateUserName(user);
        user.setUserName(userName);

        String password = RandomStringGenerator.generateRandomString(passwordLength);
        user.setPassword(password);

        return user;
    }

    private Long generateUsernameSerialNumber() {
        return usernameSerialNumberCounter++;
    }

    private Long generateUniqueId() {
        return idCounter++;
    }
}
