package com.SpringExaminationSystem.repository.exam.student;

import com.SpringExaminationSystem.model.entity.exam.Exam;
import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;
import com.SpringExaminationSystem.repository.SoftDeleteRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentExamDao extends SoftDeleteRepository<StudentExam, Integer> {

    @Query("SELECT se FROM StudentExam se WHERE se.user.userId = :userId AND se.exam.examId = :examId AND se.examStatus = :examStatus AND se.isActive = true")
    Optional<StudentExam> findByUserAndExamAndStatus(Integer userId, Integer examId, Integer examStatus);

    @Query("SELECT se FROM StudentExam se WHERE se.user.userId = :userId  AND se.examStatus = :examStatus AND se.isActive = true")
    Optional<StudentExam> findByUserAndStatus(Integer userId, Integer examStatus);

    @Query("SELECT se FROM StudentExam se WHERE se.user.userId= :userId")
    List<StudentExam> findByUser(Integer userId);

    @Query("SELECT se FROM StudentExam se WHERE se.user.authInfo.userName = :userName AND se.isActive = true")
    List<StudentExam> findByUserName(String userName);

    List<StudentExam> findByExam(Exam exam);
}
