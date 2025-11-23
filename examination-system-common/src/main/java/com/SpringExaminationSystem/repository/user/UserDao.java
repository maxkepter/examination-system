package com.SpringExaminationSystem.repository.user;

import com.SpringExaminationSystem.model.entity.user.User;
import com.SpringExaminationSystem.repository.SoftDeleteRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends SoftDeleteRepository<User, Integer> {

}
