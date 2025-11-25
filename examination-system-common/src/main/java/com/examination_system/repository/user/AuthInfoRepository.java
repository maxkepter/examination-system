package com.examination_system.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examination_system.model.entity.user.AuthInfo;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, Integer> {
    public AuthInfo findByUserName(String userName);

    public AuthInfo findByUserId(Integer userId);

    public boolean existsByUserName(String userName);
}
