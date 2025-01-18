package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    User getUserByEmail(String email);
    User addUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
