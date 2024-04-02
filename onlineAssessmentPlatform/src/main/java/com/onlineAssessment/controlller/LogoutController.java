package com.onlineAssessment.controlller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		logger.info("User logged out successfully.");
		return "homePage";
	}
}
