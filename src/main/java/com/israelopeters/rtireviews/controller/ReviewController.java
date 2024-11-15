package com.israelopeters.rtireviews.controller;

import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/")
    public ResponseEntity<List<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        review.setDateTimeCreated(LocalDateTime.now());
        reviewService.addReview(review);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
