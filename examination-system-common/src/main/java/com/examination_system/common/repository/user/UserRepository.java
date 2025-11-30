package com.examination_system.common.repository.user;

import org.springframework.stereotype.Repository;

import com.examination_system.core.repository.SoftDeleteRepository;
import com.examination_system.common.model.entity.user.User;

@Repository
public interface UserRepository extends SoftDeleteRepository<User, Long> {

}
