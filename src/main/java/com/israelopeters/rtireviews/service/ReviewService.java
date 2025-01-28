package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.dto.ReviewDto;
import com.israelopeters.rtireviews.model.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getAllReviews();
    Review getReviewById(Long id);
    void addReview(ReviewDto reviewDto);
    void editReview(Long id, Review editedReview);
    void deleteReview(Long id);
}
