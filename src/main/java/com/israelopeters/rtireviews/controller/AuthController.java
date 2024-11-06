package com.israelopeters.rtireviews.controller;

import com.israelopeters.rtireviews.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Controller for Spring MVC test purposes

@Controller
public class AuthController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("userForSignup", user);
        return "signup";
    }


}
