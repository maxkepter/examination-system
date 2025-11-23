package com.SpringExaminationSystem.repository.exam;

import com.SpringExaminationSystem.model.entity.exam.Major;
import com.SpringExaminationSystem.repository.SoftDeleteRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface MajorDao extends SoftDeleteRepository<Major, String> {

}
