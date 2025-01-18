package com.israelopeters.rtireviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {

    private String firstName;

    private String lastName;

    private String country;

    private String bio;

    private String email;

    private String password;
}
