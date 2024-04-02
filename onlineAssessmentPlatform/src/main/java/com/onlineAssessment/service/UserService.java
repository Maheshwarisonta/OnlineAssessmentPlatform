package com.onlineAssessment.service;

import java.util.List;

import com.onlineAssessment.model.Instructor;
import com.onlineAssessment.model.Student;
import com.onlineAssessment.model.User;

public interface UserService {
	public void saveUser(User user);

	public User findByUserEmail(String userEmail);

	public Instructor getByInstructorId(long userId);

	public Student getByStudentId(long userId);

	public List<Student> getAllStudents();
}
