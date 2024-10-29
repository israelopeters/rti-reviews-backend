package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.dto.UserDto;
import com.israelopeters.rtireviews.model.Role;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.repository.RoleRepository;
import com.israelopeters.rtireviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>(userRepository.findAll());
        return userList;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = new Role();
        if (role == null) {
            role = assignUser();
        }
        user.setRoles(List.of(role));
        return userRepository.save(user);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role assignUser() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }
}
