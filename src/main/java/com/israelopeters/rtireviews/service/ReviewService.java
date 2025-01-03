package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();
    void addReview(Review review);
    void editReview(Long id, Review editedReview);
    void deleteReview(Long id);
}
