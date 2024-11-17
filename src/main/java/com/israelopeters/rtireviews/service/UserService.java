package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserByEmail(String email);
    void addUser(User user);
    void updateUser(Long id, User user);
}
