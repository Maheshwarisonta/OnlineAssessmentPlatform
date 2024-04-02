package com.onlineAssessment.controlller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.onlineAssessment.model.Instructor;
import com.onlineAssessment.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class InstructorController {
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("/instructorPage")
	public String viewInstructorPage(HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Accessing instructor page for user ID: " + session.getAttribute("userId"));
			Instructor instructor = userService.getByInstructorId((long) (session.getAttribute("userId")));
			model.addAttribute("instructor", instructor);
			return "instructorPage";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/backtoinstructorpage")
	public String getBackToInstructorPage(HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Returning to instructor page for user ID: " + session.getAttribute("userId"));
			return "instructorPage";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}
}
