package com.SpringExaminationSystem.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringExaminationSystem.model.entity.user.AuthInfo;

@Repository
public interface AuthInfoDao extends JpaRepository<AuthInfo, Integer> {
    public AuthInfo findByUserName(String userName);

    public AuthInfo findByUserId(Integer userId);

    public boolean existsByUserName(String userName);
}
