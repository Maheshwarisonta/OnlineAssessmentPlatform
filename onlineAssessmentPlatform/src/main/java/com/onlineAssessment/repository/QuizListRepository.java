package com.onlineAssessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineAssessment.model.Quiz;

@Repository
public interface QuizListRepository extends JpaRepository<Quiz, Long> {
	Quiz findByQuizTitle(String quizTitle);

	Quiz findByQuizId(long quizId);

}
