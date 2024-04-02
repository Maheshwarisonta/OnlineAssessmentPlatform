package com.onlineAssessment.service;

import java.util.List;

import com.onlineAssessment.model.Quiz;

public interface QuizListService {

	void saveQuiz(Quiz quiz);

	Quiz findByQuizTitle(String quizTitle);

	List<Quiz> getAllQuizzes();

	Quiz getQuizByQuizId(long quizId);

	void deleteQuizByQuizId(long quizId);

	Quiz getByQuizIdAndStudentId(long quizId, long studentId);
}
