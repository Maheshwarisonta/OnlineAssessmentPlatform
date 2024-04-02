package com.onlineAssessment.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long quizId;
	@Column
	private String quizTitle;
	@Column
	private long instructorId;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "quiz_questions", joinColumns = @JoinColumn(name = "quizId", referencedColumnName = "quizId"), inverseJoinColumns = @JoinColumn(name = "questionId", referencedColumnName = "questionId"))
	private List<Questions> questions;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "quiz_students", joinColumns = @JoinColumn(name = "quizId", referencedColumnName = "quizId"), inverseJoinColumns = @JoinColumn(name = "studentId", referencedColumnName = "studentId"))
	private List<Student> students;

	public Quiz(String quizTitle) {
		super();
		this.quizTitle = quizTitle;
	}

	public Quiz() {
		super();
	}

	public long getQuizId() {
		return quizId;
	}

	public void setQuizId(long quizId) {
		this.quizId = quizId;
	}

	public String getQuizTitle() {
		return quizTitle;
	}

	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(long instructorId) {
		this.instructorId = instructorId;
	}
}
