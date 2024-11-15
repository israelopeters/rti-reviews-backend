package com.israelopeters.rtireviews.controller;

import com.israelopeters.rtireviews.model.Review;
import com.israelopeters.rtireviews.model.User;
import com.israelopeters.rtireviews.service.ReviewService;
import com.israelopeters.rtireviews.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

// Controller for Spring MVC test purposes

@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        User user = new User();
        model.addAttribute("userForSignup", user);
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {
        List<Review> reviewList = new ArrayList<>(reviewService.getAllReviews());
        model.addAttribute("reviewList", reviewList);
        return "reviews";
    }

    @GetMapping("reviews/post")
    public String showReviewPostForm(Model model) {
        Review review = new Review();
        model.addAttribute("reviewPost", review);
        return "post_review";
    }

    @PostMapping("/signup/save")
    public String signup(@Valid @ModelAttribute("userForSignup") User user, BindingResult bindingResult, Model model) {
        if(isUserExists(user)) {
            bindingResult.rejectValue(
                    "email", "", "This email is already registered to a user.");
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("userForSignup", user);
            return "signup";
        }
        userService.addUser(user);
        return "signup_success";
    }

    @PostMapping("/reviews/post/save")
    public String postReview(@ModelAttribute("reviewPost") Review review, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "Error";
        }
        reviewService.addReview(review);
        return "redirect:/reviews"; // Redirect to endpoint instead of template so as to load updated reviews list
    }

    private Boolean isUserExists(User user) {
        User existingUser = userService.getUserByEmail(user.getEmail());
        return (existingUser != null &&
                existingUser.getEmail() != null &&
                !existingUser.getEmail().isEmpty());
    }


}
