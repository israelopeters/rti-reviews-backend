package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void getAllUsersWhenUserTableIsEmpty() {
        //Arrange
        List<User> userListExpected = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userListExpected);

        //Act
        List<User> userListActual = userServiceImpl.getAllUsers();

        //Assert
        assertEquals(userListActual.size(), 0);
        assertEquals(userListActual, userListExpected);

    }

    @Test
    void getAllUsersWhenASingleUserExists() {

    }

    @Test
    void getAllUsersWhenMultipleUsersExist() {
        // 3 records
    }

    @Test
    void getUserByEmail() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void addUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}