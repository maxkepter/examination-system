package com.SpringExaminationSystem.repository.exam;

import com.SpringExaminationSystem.model.entity.exam.Subject;
import com.SpringExaminationSystem.repository.SoftDeleteRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface SubjectDao extends SoftDeleteRepository<Subject, String> {

}
