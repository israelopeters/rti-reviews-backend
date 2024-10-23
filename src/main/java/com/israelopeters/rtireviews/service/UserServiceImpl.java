package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>(userRepository.findAll());
        return userList;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
