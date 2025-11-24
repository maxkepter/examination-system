package com.examination_system.repository.user;

import org.springframework.stereotype.Repository;

import com.examination_system.model.entity.user.User;
import com.examination_system.repository.SoftDeleteRepository;

@Repository
public interface UserDao extends SoftDeleteRepository<User, Integer> {

}
