package com.onlineAssessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineAssessment.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	Subject findBySubjectName(String subjectName);

	Subject findBySubjectId(long subjectId);
}
