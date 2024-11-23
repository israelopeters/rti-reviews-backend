package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.exception.UserNotFoundException;
import com.israelopeters.rtireviews.model.Role;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.repository.RoleRepository;
import com.israelopeters.rtireviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>(userRepository.findAll());
        return userList;
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("No user found with this email");
        }
    }

    @Override
    public void addUser(User user) {
        user.setDateCreated(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = assignUser();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            existingUser = existingUser.map(existingUserTemp -> {
                existingUserTemp.setFirstName(updatedUser.getFirstName());
                existingUserTemp.setLastName(updatedUser.getLastName());
                existingUserTemp.setCountry(updatedUser.getCountry());
                existingUserTemp.setBio(updatedUser.getBio());
                existingUserTemp.setEmail(updatedUser.getEmail());
                existingUserTemp.setRoles(updatedUser.getRoles());
                return userRepository.save(existingUserTemp);
            });
        } else {
            throw new UserNotFoundException(
                    String.format("No user found with ID %d", id));
        }

    }

    @Override
    public void deleteUser(Long id) {
        if (isUserPresent(id)) {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
        } else {
            throw new UserNotFoundException(
                    String.format("No user found with ID %d", id));
        }
    }

    private Role assignUser() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }

    private boolean isUserPresent(Long userId) {
        return userRepository.findById(userId).isPresent();
    }

    private boolean isUserPresent(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
