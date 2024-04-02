package com.onlineAssessment.controlller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.onlineAssessment.model.Quiz;
import com.onlineAssessment.model.Student;
import com.onlineAssessment.service.SubmissionService;
import com.onlineAssessment.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentPerformanceController {
	@Autowired
	private UserService userService;

	@Autowired
	private SubmissionService submissionService;
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("/studentperformance")
	public String showStudentPerformancePage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			List<Student> studentsList = userService.getAllStudents();
			model.addAttribute("studentsList", studentsList);
			logger.info("Viewing student performance page...");
			return "studentPerformance";
		} else {
			logger.warn("Attempted unauthorized access to student performance page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewperformance/{id}")
	public String showPerformancePage(@PathVariable(value = "id") Long studentId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			session.setAttribute("studentId", studentId);
			Student student = userService.getByStudentId(studentId);
			List<Quiz> quizList = student.getQuizzes();
			Map<String, String> quizMap = new HashMap<String, String>();
			for (Quiz quiz : quizList) {
				String percentage = (String) submissionService.findByStudentIdAndQuizId(studentId, quiz.getQuizId());
				quizMap.put(quiz.getQuizTitle(), percentage);
			}
			model.addAttribute("quizMap", quizMap);
			logger.info("Viewing performance for student with ID: " + studentId);
			return "viewPerformance";
		} else {
			logger.warn("Attempted unauthorized access to performance page for student with ID: " + studentId);
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}
}
