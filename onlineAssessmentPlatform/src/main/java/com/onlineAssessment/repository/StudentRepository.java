package com.onlineAssessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineAssessment.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByStudentEmail(String studentEmail);
}
