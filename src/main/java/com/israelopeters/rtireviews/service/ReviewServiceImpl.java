package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews() {
        List<Review> reviewsList = new ArrayList<>(reviewRepository.findAll());
        return reviewsList;
    }

    @Override
    public void addReview(Review review) {
        review.setDateTimeCreated(LocalDateTime.now());
        reviewRepository.save(review);
    }
}
