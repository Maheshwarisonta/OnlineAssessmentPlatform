package com.onlineAssessment.controlller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.onlineAssessment.model.User;
import com.onlineAssessment.service.UserService;

@Controller
public class registrationController {
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("/register")
	public String showRegistrationPage() {
		logger.info("Showing registration page...");
		return "registrationPage";
	}

	@PostMapping("/register")
	public String saveUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
		logger.info("Saving user...");
		if (result.hasErrors()) {
			logger.error("Error in user registration form.");
			return "registrationPage";
		}

		User existingUser = userService.findByUserEmail(user.getUserEmail());
		if (existingUser != null) {
			logger.warn("User with this email already exists.");
			model.addAttribute("error", "User with this email already exits");
			return "registrationPage";
		}

		userService.saveUser(user);
		logger.info("User registered successfully.");
		return "redirect:/login";
	}

}
