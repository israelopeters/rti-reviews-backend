package com.israelopeters.rtireviews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReviewExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleReviewNotFoundException(ReviewNotFoundException e) {
        return new  ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
