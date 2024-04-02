package com.onlineAssessment.service;

import com.onlineAssessment.model.Submission;

public interface SubmissionService {

	void save(Submission submission);

	String findByStudentIdAndQuizId(long userId, long quizId);

	Submission findByStudentIdQuizId(long userId, long quizId);

	String getByQuestionIdAndStudentId(long questionId, long userId);

}
