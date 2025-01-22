package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.dto.UserCreationDto;
import com.israelopeters.rtireviews.exception.UserAlreadyExistsException;
import com.israelopeters.rtireviews.exception.UserNotFoundException;
import com.israelopeters.rtireviews.model.Mapper;
import com.israelopeters.rtireviews.model.Role;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.dto.UserDto;
import com.israelopeters.rtireviews.repository.RoleRepository;
import com.israelopeters.rtireviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Mapper mapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toUserDto)
                .collect(toList());
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("No user found with this email!");
        }
    }

    @Override
    public UserDto addUser(UserCreationDto userCreationDto) {
        if (isUserPresent(userCreationDto.getEmail())) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        User user = mapper.toUser(userCreationDto);
        user.setDateCreated(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = assignUser();
        }
        user.setRoles(List.of(role));
        return mapper.toUserDto(
                userRepository.save(user));
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            existingUser = existingUser.map(existingUserTemp -> {
                existingUserTemp.setFirstName(updatedUser.getFirstName());
                existingUserTemp.setLastName(updatedUser.getLastName());
                existingUserTemp.setCountry(updatedUser.getCountry());
                existingUserTemp.setBio(updatedUser.getBio());
                existingUserTemp.setRoles(updatedUser.getRoles());
                return userRepository.save(existingUserTemp);
            });
            return existingUser.get();
        } else {
            throw new UserNotFoundException(
                    String.format("No user found with ID %d", id));
        }
    }

    @Override
    public void deleteUser(Long id) {
        if (isUserPresent(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(
                    String.format("No user found with ID %d", id));
        }
    }

    private Role assignUser() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    private boolean isUserPresent(Long userId) {
        return userRepository.findById(userId).isPresent();
    }

    private boolean isUserPresent(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public String getAuthenticatedUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
