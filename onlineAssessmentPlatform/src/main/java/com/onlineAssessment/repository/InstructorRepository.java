package com.onlineAssessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineAssessment.model.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

	Instructor findByInstructorEmail(String instructorEmail);

}
