package com.israelopeters.rtireviews.controller;

import com.israelopeters.rtireviews.dto.UserCreationDto;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.dto.UserDto;
import com.israelopeters.rtireviews.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Tag(name = "get",
            description = "All GET methods")
    @Operation(summary = "Get all users",
            description = "Get all saved users")
    @ApiResponse(responseCode = "200",
            description = "All users found",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Tag(name = "get",
            description = "All GET methods")
    @Operation(summary = "Get user by email",
            description = "Get a user by a particular email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content)})
    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "Email of user to find", required = true)
            @RequestParam String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @Tag(name = "add",
            description = "All ADD methods")
    @Operation(summary = "Add user",
            description = "Add a new user")
    @ApiResponse(responseCode = "201",
            description = "User created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})
    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(
            @Parameter(description = "User to add to data store", required = true)
            @RequestBody UserCreationDto userCreationDto) {
        userService.addUser(userCreationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Tag(name = "update",
            description = "All UPDATE methods")
    @Operation(summary = "Update existing user",
            description = "Update an existing user's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found for update",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content)})
    @PutMapping("/update{id}")
    public ResponseEntity<User> updateUser(@RequestParam("id") Long id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Tag(name = "delete",
            description = "All DELETE methods")
    @Operation(summary = "Delete user",
            description = "Delete a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "User deleted"),
            @ApiResponse(responseCode = "404",
                    description = "User not found")})
    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "Id of user to delete from data store", required = true)
            @RequestParam("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
