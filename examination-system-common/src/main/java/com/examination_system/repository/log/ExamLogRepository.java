package com.examination_system.repository.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.examination_system.model.entity.log.ExamLog;

@Repository
public interface ExamLogRepository extends JpaRepository<ExamLog, Integer> {

}
