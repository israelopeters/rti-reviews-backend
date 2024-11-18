package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void editReview(Long id, Review editedReview) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isPresent()) {
            existingReview = existingReview.map(updatedReview -> {
                updatedReview.setTitle(editedReview.getTitle());
                updatedReview.setBody(editedReview.getBody());
                updatedReview.setGenreList(editedReview.getGenreList());
                return reviewRepository.save(updatedReview);
            });
        }
        // Code for unhappy path
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
