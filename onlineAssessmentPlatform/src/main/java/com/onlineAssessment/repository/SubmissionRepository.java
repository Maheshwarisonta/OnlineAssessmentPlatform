package com.onlineAssessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineAssessment.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

	List<Submission> findByStudentIdAndQuizId(long studentId, long quizId);

	List<Submission> findByQuestionIdAndStudentId(long questionId, long studentId);

}
