package com.SpringExaminationSystem.repository.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringExaminationSystem.model.entity.log.BanLog;

@Repository
public interface BanLogDao extends JpaRepository<BanLog, Integer> {

}
