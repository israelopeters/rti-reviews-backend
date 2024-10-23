package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserByEmail(String email);
}
