package com.examination_system.repository.exam;

import org.springframework.stereotype.Repository;

import com.examination_system.core.repository.SoftDeleteRepository;
import com.examination_system.model.entity.exam.Major;

@Repository
public interface MajorRepository extends SoftDeleteRepository<Major, String> {

}
