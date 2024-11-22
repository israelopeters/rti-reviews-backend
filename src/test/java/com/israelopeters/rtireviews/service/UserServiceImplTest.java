package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.exception.UserNotFoundException;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("getAllUsers() returns empty list")
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
    @DisplayName("getAllUsers() returns a single-user list")
    void getAllUsersWhenASingleUserExists() {
        //Arrange
        List<User> userListExpected = new ArrayList<>();
        User user = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password", LocalDate.now(), List.of());
        userListExpected.add(user);
        when(userRepository.findAll()).thenReturn(userListExpected);

        //Act
        List<User> userListActual = userServiceImpl.getAllUsers();

        //Assert
        assertEquals(userListActual.size(), 1);
        assertEquals(userListActual.getFirst().getFirstName(), "Israel");
        assertEquals(userListActual, userListExpected);
    }

    @Test
    @DisplayName("getAllUsers() returns a list containing 3 users")
    void getAllUsersWhenMultipleUsersExist() {
        //Arrange
        List<User> userListExpected = new ArrayList<>();
        User userOne = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password1", LocalDate.now(), List.of());

        User userTwo = new User(2L, "Samuel", "Adeyeye", "Barbados", "He is he. Hehe!",
                "samuel@email.com", "password2", LocalDate.now(), List.of());

        User userThree = new User(3L, "David", "Lawal", "USA", "Married man. Hehe!",
                "david@email.com", "password3", LocalDate.now(), List.of());

        userListExpected.add(userOne);
        userListExpected.add(userTwo);
        userListExpected.add(userThree);

        when(userRepository.findAll()).thenReturn(userListExpected);

        //Act
        List<User> userListActual = userServiceImpl.getAllUsers();

        //Assert
        assertEquals(userListActual.size(), 3);
        assertEquals(userListActual.getLast().getFirstName(), "David");
        assertEquals(userListActual, userListExpected);
    }

    @Test
    @DisplayName("getUserByEmail() throws an error when email is not in data store")
    void getUserByEmailWhenUserIsNotInDataStore() {
        //Arrange
        List<User> userList = new ArrayList<>();
        User user = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password", LocalDate.now(), List.of());
        userList.add(user);

        when(userRepository.findByEmail("peters@email.com"))
                .thenThrow(new UserNotFoundException("No user found with email."));

        //Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserByEmail("peters@email.com"));
    }

    @Test
    @DisplayName("getUserByEmail() returns a user when email is in data store")
    void getUserByEmailWhenUserIsInDataStore() {
        //Arrange
        User userExpected = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password", LocalDate.now(), List.of());
        when(userRepository.findByEmail("israel@email.com")).thenReturn(userExpected);

        //Act
        User userActual =  userRepository.findByEmail("israel@email.com");

        // Assert
        assertEquals(userActual, userExpected);
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