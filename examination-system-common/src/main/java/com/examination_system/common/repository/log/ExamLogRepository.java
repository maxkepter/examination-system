package com.examination_system.common.repository.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.examination_system.common.model.entity.log.ExamLog;

import java.util.List;

@Repository
public interface ExamLogRepository extends JpaRepository<ExamLog, Long> {
    
    @Query("SELECT el FROM ExamLog el WHERE el.studentExam.studentExamId = :studentExamId ORDER BY el.createdAt ASC")
    List<ExamLog> findByStudentExamId(@Param("studentExamId") Long studentExamId);
}
