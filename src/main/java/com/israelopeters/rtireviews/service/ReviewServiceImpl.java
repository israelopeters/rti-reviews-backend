package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.dto.ReviewDto;
import com.israelopeters.rtireviews.exception.ReviewNotFoundException;
import com.israelopeters.rtireviews.exception.UnauthorizedUserAccessException;
import com.israelopeters.rtireviews.model.Mapper;
import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    Mapper mapper;

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(mapper::toReviewDto)
                .collect(toList());
    }

    @Override
    public Review getReviewById(Long id) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isPresent()) {
            return existingReview.get();
        }
        throw new ReviewNotFoundException(
                String.format("No review found with ID: %d", id));
    }

    @Override
    public void addReview(ReviewDto reviewDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User author = userService.getUserByEmail(username);
        Review review = mapper.toReview(reviewDto);
        review.setAuthor(author);
        reviewDto.setDateTimeCreated(LocalDateTime.now());
        reviewRepository.save(review);
    }

    @Override
    public void editReview(Long id, Review editedReview) {
        Optional<Review> existingReview = reviewRepository.findById(id);

        try {
            if (existingReview.isPresent()) {
                User author = existingReview.get().getAuthor();
                String username = userService.getAuthenticatedUsername();
                User authenticetedUser = userService.getUserByEmail(username);
                if (!authenticetedUser.getEmail().equals(author.getEmail())) {
                    throw new UnauthorizedUserAccessException("You are not authorized to edit this review.");
                }

                existingReview = existingReview.map(updatedReview -> {
                    updatedReview.setTitle(editedReview.getTitle());
                    updatedReview.setBody(editedReview.getBody());
                    updatedReview.setGenreList(editedReview.getGenreList());
                    return reviewRepository.save(updatedReview);
                });
            }
        } catch (ReviewNotFoundException e) {
            throw new ReviewNotFoundException(
                    String.format("No review found with ID: %d", id));
        }
    }

    @Override
    public void deleteReview(Long id) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isPresent()) {
            reviewRepository.deleteById(id);
        } else {
            throw new ReviewNotFoundException(
                    String.format("No review found with ID: %d", id));
        }
    }
}
