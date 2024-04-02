package com.onlineAssessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineAssessment.model.Instructor;
import com.onlineAssessment.model.Student;
import com.onlineAssessment.model.User;
import com.onlineAssessment.repository.InstructorRepository;
import com.onlineAssessment.repository.StudentRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private InstructorRepository instructorRepo;

	@Override
	public User findByUserEmail(String userEmail) {
		User user = new User();
		Student student = studentRepo.findByStudentEmail(userEmail);
		if (student != null) {
			user.setUserId(student.getStudentId());
			user.setUserName(student.getStudentName());
			user.setPassword(student.getPassword());
			user.setUserEmail(student.getStudentEmail());
			user.setRole("student");
			return user;
		}

		Instructor instructor = instructorRepo.findByInstructorEmail(userEmail);
		if (instructor != null) {
			user.setUserId(instructor.getInstructorId());
			user.setUserName(instructor.getInstructorName());
			user.setUserEmail(instructor.getInstructorEmail());
			user.setPassword(instructor.getPassword());
			user.setRole("instructor");
			return user;
		}
		return null;
	}

	public void saveUser(User user) {
		if (user.getRole().equals("student")) {
			Student student = new Student();
			student.setStudentName(user.getUserName());
			student.setStudentEmail(user.getUserEmail());
			student.setPassword(user.getPassword());
			studentRepo.save(student);
		} else {
			Instructor instructor = new Instructor(user.getUserName(), user.getUserEmail(), user.getPassword());
			instructorRepo.save(instructor);
		}
	}

	@Override
	public Instructor getByInstructorId(long userId) {
		return instructorRepo.getById(userId);
	}

	@Override
	public Student getByStudentId(long userId) {
		return studentRepo.getById(userId);
	}

	@Override
	public List<Student> getAllStudents() {
		return studentRepo.findAll();
	}

}