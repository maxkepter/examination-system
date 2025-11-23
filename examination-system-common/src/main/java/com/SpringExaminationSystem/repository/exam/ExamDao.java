package com.SpringExaminationSystem.repository.exam;

import com.SpringExaminationSystem.model.entity.exam.Exam;
import com.SpringExaminationSystem.repository.SoftDeleteRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamDao extends SoftDeleteRepository<Exam, Integer> {

    @Query("SELECT e FROM Exam e LEFT JOIN FETCH e.questions WHERE e.examId = :examId AND e.isActive = true")
    Optional<Exam> findActiveByIdWithQuestions(@Param("examId") Integer examId);
}
