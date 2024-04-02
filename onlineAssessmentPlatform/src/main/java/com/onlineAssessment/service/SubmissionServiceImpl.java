package com.onlineAssessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineAssessment.model.Questions;
import com.onlineAssessment.model.Submission;
import com.onlineAssessment.repository.QuestionRepository;
import com.onlineAssessment.repository.SubmissionRepository;

@Service
public class SubmissionServiceImpl implements SubmissionService {
	@Autowired
	private SubmissionRepository submissionRepository;
	
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public void save(Submission submission) {
		 submissionRepository.save(submission);
	}
	
	@Override
	public String findByStudentIdAndQuizId(long userId, long quizId) {
		if(submissionRepository.findByStudentIdAndQuizId(userId, quizId).isEmpty()) {
			return "Not Attempted";
		}
		List<Submission> submissionList = submissionRepository.findByStudentIdAndQuizId(userId, quizId);
		double score = 0;
		double totalScore = 0;
		for(Submission submission: submissionList) {
			Questions question = questionRepository.getById(submission.getQuestionId());
			if(submission.getSelectedOption().equals(question.getCorrectAnswer())) {
				score  += question.getScore();
				System.out.println(score);
			}
			totalScore += question.getScore();
		}
		return "" + ((score/totalScore) * 100);
	}

	@Override
	public Submission findByStudentIdQuizId(long userId, long quizId) {
		List<Submission> submissionList = submissionRepository.findByStudentIdAndQuizId(userId, quizId);
		if(submissionList.isEmpty()) {
			return null;
		}
		return submissionList.get(0);
	}
	
	@Override
	public String getByQuestionIdAndStudentId(long questionId, long studentId) {
		Submission submission = submissionRepository.findByQuestionIdAndStudentId(questionId, studentId).get(0);
		return submission.getSelectedOption();
	}

}
