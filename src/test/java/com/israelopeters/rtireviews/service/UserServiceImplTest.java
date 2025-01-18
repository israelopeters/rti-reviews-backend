package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.exception.UserAlreadyExistsException;
import com.israelopeters.rtireviews.exception.UserNotFoundException;
import com.israelopeters.rtireviews.model.*;
import com.israelopeters.rtireviews.repository.RoleRepository;
import com.israelopeters.rtireviews.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@DataJpaTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private final Mapper mapper = new Mapper();

    private final Set<Review> reviews = new HashSet<>();


    @Test
    @DisplayName("getAllUsers() returns empty list")
    void getAllUsersWhenUserTableIsEmpty() {
        //Arrange
        List<User> userListRepository = new ArrayList<>();
        List<UserDto> userListExpected = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userListRepository);

        //Act
        List<UserDto> userListActual = userServiceImpl.getAllUsers();

        //Assert
        assertEquals(userListActual.size(), 0);
        assertEquals(userListActual, userListExpected);

    }

    @Test
    @DisplayName("getAllUsers() returns a single-user list")
    void getAllUsersWhenASingleUserExists() {
        //Arrange
        List<User> userListRepository = new ArrayList<>();
        List<UserDto> userListExpected = new ArrayList<>();
        User user = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password", LocalDate.now(), reviews, List.of());
        userListExpected.add(mapper.toUserDto(user));
        when(userRepository.findAll()).thenReturn(userListRepository);

        //Act
        List<UserDto> userListActual = userServiceImpl.getAllUsers();

        //Assert
        assertEquals(userListActual.size(), 1);
        assertEquals(userListActual.getFirst().getFirstName(), "Israel");
        assertEquals(userListActual, userListExpected);
    }

    @Test
    @DisplayName("getAllUsers() returns a list containing 3 users")
    void getAllUsersWhenMultipleUsersExist() {
        //Arrange
        List<User> userListRepository = new ArrayList<>();
        List<UserDto> userListExpected = new ArrayList<>();
        User userOne = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password1", LocalDate.now(), reviews, List.of());

        User userTwo = new User(2L, "Samuel", "Adeyeye", "Barbados", "He is he. Hehe!",
                "samuel@email.com", "password2", LocalDate.now(), reviews, List.of());

        User userThree = new User(3L, "David", "Lawal", "USA", "Married man. Hehe!",
                "david@email.com", "password3", LocalDate.now(), reviews, List.of());

        userListExpected.add(mapper.toUserDto(userOne));
        userListExpected.add(mapper.toUserDto(userTwo));
        userListExpected.add(mapper.toUserDto(userThree));

        when(userRepository.findAll()).thenReturn(userListRepository);

        //Act
        List<UserDto> userListActual = userServiceImpl.getAllUsers();

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
                "israel@email.com", "password", LocalDate.now(), reviews, List.of());
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
                "israel@email.com", "password", LocalDate.now(), reviews, List.of());
        when(userRepository.findByEmail("israel@email.com")).thenReturn(Optional.of(userExpected));

        //Act
        User userActual =  userRepository.findByEmail("israel@email.com").get();

        // Assert
        assertEquals(userActual, userExpected);
    }

    @Test
    @DisplayName("addUserWhenUserAlreadyExists() throws an error indicating user already exists")
    void addUserWhenUserAlreadyExists() {
        //Arrange
        User user = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password", LocalDate.now(), reviews, List.of());

        when(userRepository.findByEmail(user.getEmail())).thenThrow(new UserAlreadyExistsException("User already exists!"));

        //Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userServiceImpl.addUser(user));
    }

    @Test
    @DisplayName("addUserWhenUserDoesNotYetExist() returns persisted user with encrypted password and assigned role")
    void addUserWhenUserDoesNotYetExist() {
        //Arrange
        User user = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password", LocalDate.now(), reviews, List.of());

        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        User userExpected = user;
        userExpected.setPassword("encoded_password");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded_password");
        when(roleRepository.findByName("USER")).thenReturn(role);
        when(userRepository.save(user)).thenReturn(userExpected);

        //Act
        User userActual = userServiceImpl.addUser(user);

        //Assert
        assertEquals(userActual, userExpected);
    }

    @Test
    @DisplayName("updateUserWhenUserDoesNotExist throws an error indicating user does not exist")
    void updateUserWhenUserDoesNotExist() {
        //Arrange
        User user = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password", LocalDate.now(), reviews, List.of());
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUser(1L, user));
    }

    @Test
    @DisplayName("updateUserWhenUserExists returns updated user")
    void updateUserWhenUserExists(){
        //Arrange
        User existingUser = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password", LocalDate.now(), reviews, List.of());
        User updatedUser = new User(1L, "Israel", "Peters", "UK", "I am still me.",
                "israel@email.com", "password", LocalDate.now(), reviews, List.of());

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        //Act
        User actualUser = userServiceImpl.updateUser(1L, updatedUser);

        //Assert
        assertEquals(actualUser, updatedUser);
    }

    @Test
    @DisplayName(("deleteUserWhenUserDoesNotExist throws an exception of user not found"))
    void deleteUserWhenUserDoesNotExist() {
        //Arrange
        when(userRepository.findById(1L)).thenThrow(UserNotFoundException.class);

        //Act and Assert
        assertThrows(UserNotFoundException.class, ()-> userServiceImpl.deleteUser(1L));
    }

    @Test
    @DisplayName(("deleteUserWhenUserExists returns deleted user"))
    void deleteUserWhenUserExists() {
        //Arrange
        User existingUser = new User(1L, "Israel", "Peters", "UK", "I am me. Hehe!",
                "israel@email.com", "password", LocalDate.now(), reviews, List.of());
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).deleteById(isA(Long.class));

        //Act
        userServiceImpl.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }
}