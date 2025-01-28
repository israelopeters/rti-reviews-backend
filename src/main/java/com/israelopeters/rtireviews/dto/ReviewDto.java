package com.israelopeters.rtireviews.dto;

import com.israelopeters.rtireviews.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private String title;

    private String body;

    private String imageUri;

    private Long likeCount;

    private LocalDateTime dateTimeCreated;

    private List<Review.ReviewGenre> genreList;

}
