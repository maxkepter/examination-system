package com.examination_system.repository.user;

import org.springframework.stereotype.Repository;

import com.examination_system.core.repository.SoftDeleteRepository;
import com.examination_system.model.entity.user.User;

@Repository
public interface UserRepository extends SoftDeleteRepository<User, Integer> {

}
