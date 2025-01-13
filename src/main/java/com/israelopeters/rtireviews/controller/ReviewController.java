package com.israelopeters.rtireviews.controller;

import com.israelopeters.rtireviews.exception.ReviewNotFoundException;
import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.service.ReviewService;
import com.israelopeters.rtireviews.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Tag(name = "get", description = "All GET methods")
    @Operation(summary = "Get all reviews", description = "Get all saved reviews")
    @ApiResponse(responseCode = "200", description = "All reviews found",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Review.class))))
    @GetMapping("/")
    public ResponseEntity<List<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        try {
            Review review = reviewService.getReviewById(id);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/post")
    public ResponseEntity<Void> addReview(@RequestBody Review review) {
        // TODO: Get authenticated user and set as author of review before saving
        reviewService.addReview(review);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> editReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        try {
            reviewService.editReview(id, updatedReview);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
