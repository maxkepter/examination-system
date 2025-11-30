package com.examination_system.common.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examination_system.common.model.entity.user.AuthInfo;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {
    public AuthInfo findByUserName(String userName);

    public AuthInfo findByUserId(Long userId);

    public boolean existsByUserName(String userName);
}
