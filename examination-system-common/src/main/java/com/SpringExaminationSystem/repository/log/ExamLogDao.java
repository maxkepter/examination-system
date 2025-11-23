package com.SpringExaminationSystem.repository.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringExaminationSystem.model.entity.log.ExamLog;

@Repository
public interface ExamLogDao extends JpaRepository<ExamLog, Integer> {

}
