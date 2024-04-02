package com.onlineAssessment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Submission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long submissionId;
	@Column
	private long quizId;
	@Column
	private long questionId;
	@Column
	private long studentId;
	@Column
	private String selectedOption;

	public Submission() {

	}

	public Submission(long quizId, long questionId, long studentId, String selectedOption) {
		this.quizId = quizId;
		this.questionId = questionId;
		this.studentId = studentId;
		this.selectedOption = selectedOption;
	}

	public long getQuizId() {
		return quizId;
	}

	public void setQuizId(long quizId) {
		this.quizId = quizId;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(long submissionId) {
		this.submissionId = submissionId;
	}
}
