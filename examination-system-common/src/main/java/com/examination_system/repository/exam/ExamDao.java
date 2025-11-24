package com.examination_system.repository.exam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.examination_system.model.entity.exam.Exam;
import com.examination_system.repository.SoftDeleteRepository;

import java.util.Optional;

@Repository
public interface ExamDao extends SoftDeleteRepository<Exam, Integer> {

    @Query("SELECT e FROM Exam e LEFT JOIN FETCH e.questions WHERE e.examId = :examId AND e.isActive = true")
    Optional<Exam> findActiveByIdWithQuestions(@Param("examId") Integer examId);
}
