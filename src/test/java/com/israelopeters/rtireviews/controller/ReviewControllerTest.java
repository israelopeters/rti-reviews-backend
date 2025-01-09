package com.israelopeters.rtireviews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.israelopeters.rtireviews.exception.ReviewNotFoundException;
import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.service.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
class ReviewControllerTest {
    @Mock
    private ReviewServiceImpl reviewServiceImpl;

    @InjectMocks
    private ReviewController reviewController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    private final User user = new User();

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(reviewController).build();
        mapper = new ObjectMapper();
        // Manually register JavaTimeModule() to make Jackson support Java 8 date time APIs
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("GET / returns empty list and the OK status code")
    void getAllReviewsWhenReviewTableIsEmpty() throws Exception {
        // Arrange
        List<Review> reviewsList = List.of();
        when(reviewServiceImpl.getAllReviews()).thenReturn(reviewsList);

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/reviews/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(0));
    }

    @Test
    @DisplayName("GET / returns list of reviews and the OK status code")
    void getAllReviewsWhenReviewTableIsNotEmpty() throws Exception {
        // Arrange
        List<Review> reviewsList = List.of(
                new Review(1L, "Review 1", "Review body here!",
                        "image URI 1", 10L, LocalDateTime.now(), List.of(), user),
                new Review(2L, "Review 2", "Another review body here!",
                        "image URI 2", 74L, LocalDateTime.now(), List.of(), user)
        );
        when(reviewServiceImpl.getAllReviews()).thenReturn(reviewsList);

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/reviews/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(2L));
    }

    @Test
    @DisplayName("GET /{id} returns review with id = id and the OK status code")
    void getReviewByIdReturnsReviewWithGivenId() throws Exception {
        // Arrange
        Review review = new Review(5L, "Review 1", "Review body here!",
                "image URI 1", 10L, LocalDateTime.now(), List.of(), user);
        when(reviewServiceImpl.getReviewById(5L)).thenReturn(review);

        // Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/reviews/5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(5L));
    }

    @Test
    @DisplayName("GET /{id} throws ReviewNotFoundException and returns the NOT_FOUND status code")
    void getReviewByIdThrowsReviewNotFoundException() throws Exception {
        // Arrange
        when(reviewServiceImpl.getReviewById(1L)).thenThrow(ReviewNotFoundException.class);

        // Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/reviews/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /post returns the CREATED status code")
    void addReviewReturnsCreatedStatusCode() throws Exception {
        // Arrange
        Review review = new Review(1L, "Review 1", "Review body here!",
                "image URI 1", 10L, LocalDateTime.now(), List.of(), user);

        // Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/reviews/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(review)) // Hangs on manual JavaTimeModule() registration
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        /* TODO: Test whether the reviewService.addReview(review) line is executed in the /post endpoint method;
            May need to make method non-void
         */
    }

    @Test
    @DisplayName("PUT /edit/{id} for an existing review returns the ACCEPTED status code")
    void editReviewForExistingReviewReturnsAcceptedStatusCode() throws Exception {
        // Arrange
        Review editedReview = new Review(1L, "Edited Review", "Review body here!",
                "image URI 1", 10L, LocalDateTime.now(), List.of(), user);

        // Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/reviews/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(editedReview))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
        /* TODO: Test whether reviewService.editReview(id, review) is executed in /edit/{id} endpoint method
            May need to make method non-void.
         */
    }
}