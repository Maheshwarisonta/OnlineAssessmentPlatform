package com.onlineAssessment.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Instructor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long instructorId;

	@Column(length = 20)
	private String instructorName;

	@Column(length = 20)
	private String instructorEmail;

	@Column(length = 20)
	private String password;

	public Instructor() {

	}

	public long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(long instructorId) {
		this.instructorId = instructorId;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public String getInstructorEmail() {
		return instructorEmail;
	}

	public void setInstructorEmail(String instructorEmail) {
		this.instructorEmail = instructorEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Instructor(String instructorName, String instructorEmail, String password) {
		this.instructorName = instructorName;
		this.instructorEmail = instructorEmail;
		this.password = password;
	}
}