package com.israelopeters.rtireviews.dto;

import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.model.User;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReviewDto {

    private Long id;

    private String title;

    private String body;

    private String imageUri;

    private Long likeCount;

    private LocalDateTime dateTimeCreated;

    private List<Review.ReviewGenre> genreList;

    private UserDto author;

}
