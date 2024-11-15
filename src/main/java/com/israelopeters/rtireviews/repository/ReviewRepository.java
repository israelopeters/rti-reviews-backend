package com.israelopeters.rtireviews.repository;


import com.israelopeters.rtireviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Add methods for custom filters
}
