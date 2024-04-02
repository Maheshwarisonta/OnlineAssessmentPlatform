package com.onlineAssessment.controlller;

import org.springframework.stereotype.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlineAssessment.exception.UserNotFoundException;
import com.onlineAssessment.model.User;
import com.onlineAssessment.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("/login")
	public String showLoginPage() {
		return "loginPage";
	}

	@PostMapping("/login")
	public String login(@RequestParam String userEmail, @RequestParam String password, HttpServletRequest req,
			Model model) {
		User user = userService.findByUserEmail(userEmail);
		if (user != null && user.getPassword().equals(password)) {
			HttpSession session = req.getSession();
			session.setAttribute("userId", user.getUserId());
			session.setAttribute("username", user.getUserName());
			session.setAttribute("email", user.getUserEmail());
			session.setAttribute("password", user.getPassword());
			session.setAttribute("userRole", user.getRole());
			if (user.getRole().equals("student")) {
				logger.info("Student logged in successfully: " + user.getUserName());
				return "studentPage";
			}
			logger.info("Instructor logged in successfully: " + user.getUserName());
			return "instructorPage";
		} else {
			if (user == null)
				throw new UserNotFoundException();
			logger.warn("Login failed for user with email: " + user.getUserEmail());
			model.addAttribute("error", "Invalid Password");
			return "loginPage";
		}
	}
}
