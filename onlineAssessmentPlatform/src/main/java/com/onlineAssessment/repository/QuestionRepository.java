package com.onlineAssessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineAssessment.model.Questions;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {

	Questions findByQuestionTitle(String questionTitle);

	List<Questions> findBySubjectId(long subjectId);

	Questions findByQuestionId(long questionId);
}
