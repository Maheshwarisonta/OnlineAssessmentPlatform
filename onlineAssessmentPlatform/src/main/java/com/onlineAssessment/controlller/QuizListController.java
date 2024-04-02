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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.onlineAssessment.exception.QuestionNotFoundException;
import com.onlineAssessment.exception.UserNotFoundException;
import com.onlineAssessment.model.Questions;
import com.onlineAssessment.model.Quiz;
import com.onlineAssessment.model.Student;
import com.onlineAssessment.model.Subject;
import com.onlineAssessment.repository.QuestionRepository;
import com.onlineAssessment.repository.StudentRepository;
import com.onlineAssessment.service.QuizListService;
import com.onlineAssessment.service.SubjectService;
import com.onlineAssessment.service.SubmissionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuizListController {
	@Autowired
	private QuizListService quizListService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private SubmissionService submissionService ;
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("/quizlist")
	public String showQuizListPage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Accessing quiz list page...");
			List<Quiz> quizzesList = quizListService.getAllQuizzes();
			model.addAttribute("listOfQuizzes", quizzesList);
			return "quizList";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/addquiz")
	public String showAddQuiz(HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Accessing add quiz page...");
			return "addQuiz";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@PostMapping("/addquiz")
	public String addQuiz(@ModelAttribute("quiz") Quiz quiz, HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Adding new quiz...");
			quiz.setInstructorId((long) session.getAttribute("userId"));
			quizListService.saveQuiz(quiz);
			return "redirect:/quizlist";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/updatequizname/{id}")
	public String updatequizname(@PathVariable(value = "id") long quizId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
		    logger.info("Accessing update quiz name page...");
			Quiz quiz = quizListService.getQuizByQuizId(quizId);
			model.addAttribute("quiz", quiz);
			model.addAttribute("quizTitle", quiz.getQuizTitle());
			return "updateQuizName";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/deletequiz/{id}")
	public String deleteQuiz(@PathVariable(value = "id") long quizId, HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Deleting quiz...");
			quizListService.deleteQuizByQuizId(quizId);
			return "redirect:/quizlist";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewquestions/{id}")
	public String showViewQuestionsPage(@PathVariable(value = "id") long quizId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			Quiz quiz = quizListService.getQuizByQuizId(quizId);
			List<Questions> questionsList = quiz.getQuestions();
			session.setAttribute("quizId", quizId);
			model.addAttribute("listOfQuestions", questionsList);
			return "viewQuestions";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewquestions")
	public String showViewQuestionsPage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Accessing view questions page...");
			Quiz quiz = quizListService.getQuizByQuizId((long) session.getAttribute("quizId"));
			List<Questions> questionsList = quiz.getQuestions();
			session.setAttribute("quizId", (long) session.getAttribute("quizId"));
			model.addAttribute("listOfQuestions", questionsList);
			return "viewQuestions";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/subjectlist")
	public String showSubjectListPage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Accessing subject list page...");
			List<Subject> subjectList = subjectService.getAllSubjects();
			model.addAttribute("listOfSubjects", subjectList);
			return "subjectList";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewsubjectquestions/{id}")
	public String showsubjectquestionsPage(@PathVariable(value = "id") long subjectId, Model model,
			HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			List<Questions> questionsList = subjectService.getAllQuestions(subjectId);
			session.setAttribute("subjectId", subjectId);
			model.addAttribute("listOfQuestions", questionsList);
			return "viewSubjectQuestions";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewsubjectquestions")
	public String showsubjectquestionsPage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Accessing subject questions page...");
			List<Questions> questionsList = subjectService.getAllQuestions((long) session.getAttribute("subjectId"));
			session.setAttribute("subjectId", (long) session.getAttribute("subjectId"));
			model.addAttribute("listOfQuestions", questionsList);
			return "viewSubjectQuestions";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/addquestiontoquiz/{id}")
	public String addQuestionToQuiz(@PathVariable(value = "id") long questionId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Adding question to quiz...");
			Quiz quiz = quizListService.getQuizByQuizId((long) session.getAttribute("quizId"));
			Questions question = questionRepo.getById(questionId);
			List<Questions> questionsList = quiz.getQuestions();
			questionsList.add(question);
			quizListService.saveQuiz(quiz);
			return "redirect:/viewquestions";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewstudents/{id}")
	public String showViewStudnetsPage(@PathVariable(value = "id") long quizId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			Quiz quiz = quizListService.getQuizByQuizId(quizId);
			List<Student> studentsList = quiz.getStudents();
			Map<Student, String> studentMap = new HashMap<Student, String>();
			for(Student student: studentsList ) {
				String percentage = (String) submissionService.findByStudentIdAndQuizId(student.getStudentId(),  quizId);
				studentMap.put(student, percentage);
			}
			session.setAttribute("quizId", quizId);
			model.addAttribute("studentMap", studentMap);
			return "viewStudents";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewstudents")
	public String showViewStudentsPage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Accessing view students page...");
			Quiz quiz = quizListService.getQuizByQuizId((long) session.getAttribute("quizId"));
			List<Student> studentsList = quiz.getStudents();
			model.addAttribute("listOfStudents", studentsList);
			return "viewStudents";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/addstudents/{id}")
	public String addStudentToQuiz(@PathVariable(value = "id") long studentId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Adding student to quiz...");
			Quiz quiz = quizListService.getQuizByQuizId((long) session.getAttribute("quizId"));
			if(quiz.getQuestions().isEmpty())
				throw new QuestionNotFoundException();
			Student student = studentRepo.getById(studentId);
			List<Student> studentsList = quiz.getStudents();
			studentsList.add(student);
			quizListService.saveQuiz(quiz);
			return "redirect:/viewstudents";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/showallstudents/{id}")
	public String showAllStudents(@PathVariable(value = "id") long quizId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))) {
			logger.info("Showing all students...");
			Quiz quiz = quizListService.getQuizByQuizId(quizId);
			session.setAttribute("quizId", quizId);
			model.addAttribute("quiz", quiz);
			List<Student> studentsList = studentRepo.findAll();
			model.addAttribute("listOfStudents", studentsList);
			return "addStudents";
		} else {
			logger.warn("Attempted unauthorized access to instructor page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}
}
