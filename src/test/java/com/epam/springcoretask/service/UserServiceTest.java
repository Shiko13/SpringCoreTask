package com.epam.springcoretask.service;

import com.epam.springcoretask.dao.UserDao;
import com.epam.springcoretask.error.NotFoundException;
import com.epam.springcoretask.mapper.UserMapper;
import com.epam.springcoretask.model.User;
import com.epam.springcoretask.model.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Test
    void getById_WhenExists_shouldReturnDto() {
        Long userId = 1L;
        User user = new User("John", "Doe", true);
        user.setId(userId);

        when(userDao.getById(userId)).thenReturn(Optional.of(user));

        UserDto result = userService.getById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertTrue(result.getIsActive());

        verify(userDao).getById(userId);
    }

    @Test
    void getById_WhenUserDoesNotExist_shouldThrowException() {
        Long userId = 1L;

        when(userDao.getById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(userId));

        verify(userDao).getById(userId);
    }

    @Test
    void testGetAll_shouldReturnListOfDto() {
        User user1 = new User("John", "Doe", true);
        user1.setId(1L);
        User user2 = new User("Jane", "Smith", false);
        user2.setId(2L);
        List<User> users = List.of(
                user1, user2
        );

        when(userDao.getAll()).thenReturn(users);

        Collection<UserDto> result = userService.getAll();

        assertNotNull(result);
        assertEquals(users.size(), result.size());

        List<UserDto> expectedDtos = users.stream()
                                          .map(UserMapper::toUserDto)
                                          .toList();

        assertTrue(result.containsAll(expectedDtos));

        verify(userDao).getAll();
    }

    @Test
    void save_shouldReturnSavedDto() {
        User user = new User("John", "Doe", true);
        user.setId(1L);

        when(userDao.save(user)).thenReturn(user);

        UserDto result = userService.save(user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertTrue(result.getIsActive());

        verify(userDao).save(user);
    }

    @Test
    void clearData_shouldOk() {
        Mockito.doNothing().when(userDao).clearData();

        Assertions.assertDoesNotThrow(() -> userService.clearData());
    }
}
