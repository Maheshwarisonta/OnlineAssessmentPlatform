package com.onlineAssessment.controlller;

import java.util.ArrayList;
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

import com.onlineAssessment.exception.AnswerNotFoundException;
import com.onlineAssessment.exception.UserNotFoundException;
import com.onlineAssessment.model.Questions;
import com.onlineAssessment.model.Quiz;
import com.onlineAssessment.model.Student;
import com.onlineAssessment.model.Submission;
import com.onlineAssessment.repository.QuestionRepository;
import com.onlineAssessment.service.QuizListService;
import com.onlineAssessment.service.SubmissionService;
import com.onlineAssessment.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {
	@Autowired
	private UserService userService;
	@Autowired
	private QuizListService quizListService;
	@Autowired
	private QuestionRepository questionRepo;
	@Autowired
	private SubmissionService submissionService;
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("/studentPage")
	public String viewStudentPage(HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))) {
			Student student = userService.getByStudentId((long) (session.getAttribute("userId")));
			model.addAttribute("student", student);
			logger.info("Viewing student page...");
			return "studentPage";
		} else {
			logger.warn("Attempted unauthorized access to student page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}
	
	@GetMapping("/backtostudentpage")
	public String getBackToStudentPage(HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))) {
			logger.info("Returning back to student page...");
			return "studentPage";
		} else {
			logger.warn("Attempted unauthorized access to student page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/studentquizlist")
	public String viewStudentQuizListPage(HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))) {
			Student student = userService.getByStudentId((long) session.getAttribute("userId"));
			List<Quiz> quizList = student.getQuizzes();
			List<Quiz> newQuizList = new ArrayList<Quiz>();
			for (Quiz quiz : quizList) {
				Submission sub = submissionService.findByStudentIdQuizId((long) session.getAttribute("userId"),
						quiz.getQuizId());
				if (sub == null) {
					newQuizList.add(quiz);
				}
			}
			model.addAttribute("listOfUnattemptedQuizzes", newQuizList);
			logger.info("Viewing student quiz list page...");
			return "studentQuizList";
		} else {
			logger.warn("Attempted unauthorized access to istudent page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/startquiz/{id}")
	public String showStartQuizPage(@PathVariable(value = "id") long quizId, HttpSession session, Model model) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))) {
			Quiz quiz = quizListService.getQuizByQuizId(quizId);
			List<Questions> questions = quiz.getQuestions();
			model.addAttribute("question", questions.get(0));
			model.addAttribute("quizId", quizId);
			model.addAttribute("studentId", session.getAttribute("userId"));
			session.setAttribute("index", 0);
			session.setAttribute("score", 0);
			session.setAttribute("totalScore", questions.get(0).getScore());
			logger.info("Starting quiz...");
			return "startQuiz";
		} else {
			logger.warn("Attempted unauthorized access to student page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/submitquiz")
	public String submitQuiz(@ModelAttribute Submission submission, Model model, HttpSession session) {
		if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("student")) {
			if(submission.getSelectedOption() == null)
				throw new AnswerNotFoundException();
			Quiz quiz = quizListService.getQuizByQuizId(submission.getQuizId());
			List<Questions> questions = quiz.getQuestions();
			Questions question = questionRepo.getById(submission.getQuestionId());
			System.out.println(submission);
			System.out.println(submission);
			if (submission.getSelectedOption().equals(question.getCorrectAnswer())) {
				int score = (int) session.getAttribute("score");
				score += question.getScore();
				session.setAttribute("score", score);
			}
			System.out.println(session.getAttribute("score"));
			submissionService.save(submission);

			int index = (int) session.getAttribute("index");
			if (questions.size() != index + 1) {
				int totalScore = (int) session.getAttribute("totalScore");
				session.setAttribute("totalScore", totalScore + questions.get(index + 1).getScore());
				model.addAttribute("question", questions.get(index + 1));
				model.addAttribute("quizId", submission.getQuizId());
				model.addAttribute("studentId", submission.getStudentId());
				session.setAttribute("index", index + 1);
				return "startQuiz";
			}
			model.addAttribute("score", session.getAttribute("score"));
			model.addAttribute("totalScore", session.getAttribute("totalScore"));
			logger.info("Quiz submitted.");
			return "viewQuizScore";
		} else {
			logger.warn("Attempted unauthorized access to student page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/attemptedquizperformance")
	public String showAttemptedQuizzesPerformancePage(Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))) {
			Student student = userService.getByStudentId((long) session.getAttribute("userId"));
			List<Quiz> quizList = student.getQuizzes();
			Map<Quiz, String> quizMap = new HashMap<Quiz, String>();
			for (Quiz quiz : quizList) {
				String percentage =  submissionService
						.findByStudentIdAndQuizId((long) session.getAttribute("userId"), quiz.getQuizId());
				if(percentage != "Not Attempted")
					quizMap.put(quiz, percentage);
			}
			model.addAttribute("quizMap", quizMap);
			logger.info("Viewing attempted quiz performance...");
			return "attemptedQuizPerformance";
		} else {
			logger.warn("Attempted unauthorized access to student page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}

	@GetMapping("/viewpaper/{id}")
	public String viewPaperPage(@PathVariable(value = "id") long quizId, Model model, HttpSession session) {
		if ((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))) {
			Quiz quiz = quizListService.getByQuizIdAndStudentId((quizId), (long) session.getAttribute("userId"));
			Map<Questions, String> questionMap = new HashMap<>();
			List<Questions> questions = quiz.getQuestions();
			for (Questions question : questions) {
				String selectedOption = submissionService.getByQuestionIdAndStudentId(question.getQuestionId(),
						(long) session.getAttribute("userId"));
				questionMap.put(question, selectedOption);
			}
			model.addAttribute("questionMap", questionMap);
			logger.info("Viewing answer paper...");
			return "viewAnswerPaper";
		} else {
			logger.warn("Attempted unauthorized access to student page.");
			model.addAttribute("error", "Invalid EmailId or Password");
			return "loginPage";
		}
	}
}
