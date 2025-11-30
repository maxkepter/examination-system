package com.examination_system.common.repository.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.examination_system.common.model.entity.log.ExamLog;

@Repository
public interface ExamLogRepository extends JpaRepository<ExamLog, Long> {

}
