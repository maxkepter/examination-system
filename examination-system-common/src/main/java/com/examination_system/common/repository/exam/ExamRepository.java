package com.examination_system.common.repository.exam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.examination_system.core.repository.SoftDeleteRepository;
import com.examination_system.common.model.entity.exam.Exam;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends SoftDeleteRepository<Exam, Long> {

    @Query("SELECT e FROM Exam e LEFT JOIN FETCH e.questions WHERE e.examId = :examId AND e.isActive = true")
    Optional<Exam> findActiveByIdWithQuestions(@Param("examId") Long examId);

    @Query("SELECT e FROM Exam e WHERE e.examCode = :code AND e.isActive = true")
    Optional<Exam> findExamByCode(String code);

    @Query("SELECT e FROM Exam e WHERE e.subject.subjectCode = :subjectCode AND e.isActive = true")
    List<Exam> findBySubjectCode(@Param("subjectCode") String subjectCode);
}
