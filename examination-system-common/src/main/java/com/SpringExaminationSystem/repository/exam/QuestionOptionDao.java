package com.SpringExaminationSystem.repository.exam;

import com.SpringExaminationSystem.model.entity.exam.QuestionOption;
import com.SpringExaminationSystem.repository.SoftDeleteRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface QuestionOptionDao extends SoftDeleteRepository<QuestionOption, Integer> {
}
