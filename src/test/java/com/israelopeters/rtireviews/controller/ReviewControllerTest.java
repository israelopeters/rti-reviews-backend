package com.israelopeters.rtireviews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.service.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(reviewController).build();
        mapper = new ObjectMapper();
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
                        "image URI 1", 10L, LocalDateTime.now(), List.of()),
                new Review(2L, "Review 2", "Another review body here!",
                        "image URI 2", 74L, LocalDateTime.now(), List.of())
        );
        when(reviewServiceImpl.getAllReviews()).thenReturn(reviewsList);

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/reviews/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(2L));
    }
}