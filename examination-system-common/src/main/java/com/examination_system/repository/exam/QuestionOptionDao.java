package com.examination_system.repository.exam;



import org.springframework.stereotype.Repository;

import com.examination_system.model.entity.exam.QuestionOption;
import com.examination_system.repository.SoftDeleteRepository;

@Repository
public interface QuestionOptionDao extends SoftDeleteRepository<QuestionOption, Integer> {
}
