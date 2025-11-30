package com.examination_system.common.repository.exam;

import com.examination_system.common.model.entity.exam.Question;
import com.examination_system.core.repository.SoftDeleteRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends SoftDeleteRepository<Question, Long>, JpaSpecificationExecutor<Question> {

    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.options WHERE q.isActive = true")
    List<Question> findAllActiveWithOptions();

}
