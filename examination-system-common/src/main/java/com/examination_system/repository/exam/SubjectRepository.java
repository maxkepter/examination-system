package com.examination_system.repository.exam;

import org.springframework.stereotype.Repository;

import com.examination_system.core.repository.SoftDeleteRepository;
import com.examination_system.model.entity.exam.Subject;

@Repository
public interface SubjectRepository extends SoftDeleteRepository<Subject, String> {

}
