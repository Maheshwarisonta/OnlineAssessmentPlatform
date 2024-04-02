package com.onlineAssessment.service;

import java.util.List;

import com.onlineAssessment.model.Questions;
import com.onlineAssessment.model.Subject;

public interface SubjectService {

	Subject findBySubjectName(String subjectName);

	void saveSubject(Subject subject);

	List<Subject> getAllSubjects();

	void saveQuestion(Questions question);

	Questions findByQuestionTitle(String questionTitle);
	
	List<Questions> getAllQuestions(long subjectId);

	Subject getSubjectBySubjectId(long subjectId);

	void deleteSubjectBySubjectId(long subjectId);

	Questions getQuestionByQuestionId(long questionId);

	void deleteQuestionByQuestionId(long questionId);

}
