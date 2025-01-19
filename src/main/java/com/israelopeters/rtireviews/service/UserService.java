package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.dto.UserCreationDto;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    User getUserByEmail(String email);
    UserDto addUser(UserCreationDto userCreationDto);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
