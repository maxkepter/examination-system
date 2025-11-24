package com.examination_system.repository.exam;

import org.springframework.stereotype.Repository;

import com.examination_system.model.entity.exam.Subject;
import com.examination_system.repository.SoftDeleteRepository;

@Repository
public interface SubjectDao extends SoftDeleteRepository<Subject, String> {

}
