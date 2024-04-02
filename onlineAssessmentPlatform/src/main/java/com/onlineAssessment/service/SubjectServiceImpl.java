package com.onlineAssessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineAssessment.model.Questions;
import com.onlineAssessment.model.Subject;
import com.onlineAssessment.repository.QuestionRepository;
import com.onlineAssessment.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectRepository subjectRepo;

	@Autowired
	private QuestionRepository questionRepo;

	@Override
	public Subject findBySubjectName(String subjectName) {
		return subjectRepo.findBySubjectName(subjectName);
	}

	@Override
	public void saveSubject(Subject subject) {
		subjectRepo.save(subject);
	}

	@Override
	public List<Subject> getAllSubjects() {
		return subjectRepo.findAll();
	}

	@Override
	public void saveQuestion(Questions question) {
		questionRepo.save(question);
	}

	@Override
	public Questions findByQuestionTitle(String questionTitle) {
		return questionRepo.findByQuestionTitle(questionTitle);
	}

	@Override
	public List<Questions> getAllQuestions(long subjectId) {
		return questionRepo.findBySubjectId(subjectId);
	}

	@Override
	public Subject getSubjectBySubjectId(long subjectId) {
		return subjectRepo.findBySubjectId(subjectId);
	}

	@Override
	public void deleteSubjectBySubjectId(long subjectId) {
		subjectRepo.deleteById(subjectId);

	}

	@Override
	public Questions getQuestionByQuestionId(long questionId) {
		return questionRepo.findByQuestionId(questionId);
	}

	@Override
	public void deleteQuestionByQuestionId(long questionId) {
		questionRepo.deleteById(questionId);
	}
}
