package com.israelopeters.rtireviews.model;

import com.israelopeters.rtireviews.dto.ReviewDto;
import com.israelopeters.rtireviews.dto.UserCreationDto;
import com.israelopeters.rtireviews.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;

@Component
public class Mapper {
    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        return dto;
    }

    public User toUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles());
        return user;
    }

    public User toUser(UserCreationDto userCreationDto) {
        User user = new User();
        user.setFirstName(userCreationDto.getFirstName());
        user.setLastName(userCreationDto.getLastName());
        user.setEmail(userCreationDto.getEmail());
        user.setPassword(userCreationDto.getPassword());
        user.setRoles(new ArrayList<>());
        user.setReviews(new HashSet<>());
        return user;
    }

    public ReviewDto toReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setTitle(review.getTitle());
        dto.setBody(review.getBody());
        dto.setImageUri(review.getImageUri());
        dto.setLikeCount(review.getLikeCount());
        dto.setDateTimeCreated(review.getDateTimeCreated());
        dto.setGenreList(review.getGenreList());
        dto.setAuthor(toUserDto(review.getAuthor()));
        return dto;
    }
}
