package com.examination_system.common.repository.exam;

import org.springframework.stereotype.Repository;

import com.examination_system.core.repository.SoftDeleteRepository;
import com.examination_system.common.model.entity.exam.QuestionOption;

@Repository
public interface QuestionOptionRepository extends SoftDeleteRepository<QuestionOption, Long> {
}
