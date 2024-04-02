package com.onlineAssessment.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineAssessment.model.Quiz;
import com.onlineAssessment.model.Student;
import com.onlineAssessment.repository.QuizListRepository;
import com.onlineAssessment.repository.StudentRepository;

@Service
public class QuizListServiceImpl implements QuizListService {
	@Autowired
	private QuizListRepository quizListRepo;

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public void saveQuiz(Quiz quiz) {
		quizListRepo.save(quiz);
	}

	@Override
	public Quiz findByQuizTitle(String quizTitle) {
		return quizListRepo.findByQuizTitle(quizTitle);
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		return quizListRepo.findAll();
	}

	@Override
	public Quiz getQuizByQuizId(long quizId) {
		return quizListRepo.findByQuizId(quizId);
	}

	@Override
	public void deleteQuizByQuizId(long quizId) {
		quizListRepo.deleteById(quizId);
	}

	@Override
	public Quiz getByQuizIdAndStudentId(long quizId, long studentId) {
		Student student = studentRepository.getById(studentId);
		for (Quiz quiz : student.getQuizzes()) {
			if (quiz.getQuizId() == quizId) {
				return quiz;
			}
		}
		return null;
	}
}
