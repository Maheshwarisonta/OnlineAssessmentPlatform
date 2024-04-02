package com.onlineAssessment.controlller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.onlineAssessment.model.Questions;
import com.onlineAssessment.model.Subject;
import com.onlineAssessment.service.SubjectService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SubjectController {
	@Autowired
	private SubjectService subjectService;
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("/subject")
	public String showSubjectPage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			List<Subject> subjectList = subjectService.getAllSubjects();
			model.addAttribute("listOfSubjects", subjectList);
			logger.info("Viewing subject page...");
			return "subject";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/addsubject")
	public String showAddSubjectPage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
		    logger.info("Viewing add subject page...");
			return "addSubject";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewquestionbank/{id}")
	public String showViewQuestionBankPage(@PathVariable(value = "id") long subjectId, Model model,
			HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			List<Questions> questionsList = subjectService.getAllQuestions(subjectId);
			session.setAttribute("subjectId", subjectId);
			model.addAttribute("listOfQuestions", questionsList);
			logger.info("Viewing question bank page for subject with ID: " + subjectId);
			return "viewQuestionBank";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewquestionbank")
	public String showViewQuestionBankPage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			List<Questions> questionsList = subjectService.getAllQuestions((long) session.getAttribute("subjectId"));
			session.setAttribute("subjectId", (long) session.getAttribute("subjectId"));
			model.addAttribute("listOfQuestions", questionsList);
		    logger.info("Viewing question bank page...");
			return "viewQuestionBank";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@PostMapping("/addsubject")
	public String saveSubject(@ModelAttribute("subject") Subject subject, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			Subject existingSubject = subjectService.findBySubjectName(subject.getSubjectName());
			if (existingSubject != null) {
				model.addAttribute("error", "Subject with this name already exists");
				return "addSubject";
			}
			subjectService.saveSubject(subject);
			logger.info("Adding new subject: " + subject.getSubjectName());
			return "redirect:/subject";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/addquestion")
	public String showAddQuestionPage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			Questions question = new Questions();
			model.addAttribute("question", question);
			logger.info("Viewing add question page...");
			return "addQuestion";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@PostMapping("/addquestion")
	public String saveQuestion(@ModelAttribute("question") Questions question, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			question.setSubjectId((long) session.getAttribute("subjectId"));
			question.setInstructorId((long) session.getAttribute("userId"));
			subjectService.saveQuestion(question);
		    logger.info("Adding new question to subject with ID: " + session.getAttribute("subjectId"));
			return "redirect:/viewquestionbank";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/updatesubjectname/{id}")
	public String updatesubjectname(@PathVariable(value = "id") long subjectId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			Subject subject = subjectService.getSubjectBySubjectId(subjectId);
			model.addAttribute("subject", subject);
			model.addAttribute("subjectName", subject.getSubjectName());
			logger.info("Updating subject name for subject with ID: " + subjectId);
			return "updateSubjectName";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@PostMapping("/updatesubject")
	public String updateSubject(@ModelAttribute Subject subject, HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			subjectService.saveSubject(subject);
			logger.info("Updating subject with ID: " + subject.getSubjectId());
			return "redirect:/subject";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/deletesubject/{id}")
	public String deletesubject(@PathVariable(value = "id") long subjectId, HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			subjectService.deleteSubjectBySubjectId(subjectId);
			logger.info("Deleting subject with ID: " + subjectId);
			return "redirect:/subject";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/updatequestion/{id}")
	public String updatequestion(@PathVariable(value = "id") long questionId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			Questions question = subjectService.getQuestionByQuestionId(questionId);
			model.addAttribute("question", question);
			logger.info("Updating question with ID: " + questionId);
			return "updateQuestion";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@PostMapping("/updatequestion")
	public String updatequestion(@ModelAttribute Questions question, HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			question.setSubjectId((long) session.getAttribute("subjectId"));
			subjectService.saveQuestion(question);
			logger.info("Updating question with ID: " + question.getQuestionId());
			return "redirect:/viewquestionbank";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/deletequestion/{id}")
	public String deleteQuestion(@PathVariable(value = "id") long questionId, HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			subjectService.deleteQuestionByQuestionId(questionId);
			logger.info("Deleting question with ID: " + questionId);
			return "redirect:/viewquestionbank";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}
}
