package com.israelopeters.rtireviews.controller;

import com.israelopeters.rtireviews.dto.ReviewDto;
import com.israelopeters.rtireviews.exception.ReviewNotFoundException;
import com.israelopeters.rtireviews.exception.UnauthorizedUserAccessException;
import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.service.ReviewService;
import com.israelopeters.rtireviews.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Tag(name = "get", description = "All GET methods")
    @Operation(summary = "Get all reviews", description = "Get all saved reviews")
    @ApiResponse(responseCode = "200", description = "All reviews found",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Review.class))))
    @GetMapping("/")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @Tag(name = "get", description = "All GET methods")
    @Operation(summary = "Get review by id", description = "Get a review by a particular id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Review found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Review.class)))),
            @ApiResponse(responseCode = "404",
                    description = "Review not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        try {
            Review review = reviewService.getReviewById(id);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Tag(name = "add", description = "All ADD methods")
    @Operation(summary = "Add review", description = "Add a new review")
    @ApiResponse(responseCode = "201",
            description = "Review created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Review.class))})
    @PostMapping("/post")
    public ResponseEntity<Void> addReview(@RequestBody ReviewDto reviewDto) {
        reviewService.addReview(reviewDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Tag(name = "update", description = "All UPDATE methods")
    @Operation(summary = "Edit existing review", description = "Edit some fields of an existing review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Review found for editing",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Review.class)))),
            @ApiResponse(responseCode = "404",
                    description = "Review not found",
                    content = @Content)})
    @PutMapping("/edit{id}")
    public ResponseEntity<Void> editReview(@RequestParam("id") Long id, @RequestBody Review updatedReview) {
        try {
            reviewService.editReview(id, updatedReview);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserAccessException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Tag(name = "delete", description = "All DELETE methods")
    @Operation(summary = "Delete review", description = "Delete a review by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Review deleted"),
            @ApiResponse(responseCode = "404",
                    description = "Review not found")})
    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> deleteReview(@RequestParam("id") Long id) {
        try {
            reviewService.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
