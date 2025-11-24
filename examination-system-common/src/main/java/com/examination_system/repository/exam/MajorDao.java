package com.examination_system.repository.exam;

import org.springframework.stereotype.Repository;

import com.examination_system.model.entity.exam.Major;
import com.examination_system.repository.SoftDeleteRepository;

@Repository
public interface MajorDao extends SoftDeleteRepository<Major, String> {

}
