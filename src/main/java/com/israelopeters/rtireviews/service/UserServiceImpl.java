package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.dto.UserDto;
import com.israelopeters.rtireviews.model.Role;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.repository.RoleRepository;
import com.israelopeters.rtireviews.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = new Role();
        if (role == null) {
            role = assignAdmin();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role assignAdmin() {
        Role role = new Role();
        role.setName("ADMIN");
        return roleRepository.save(role);
    }
}
