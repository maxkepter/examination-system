package com.SpringExaminationSystem;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;
import com.SpringExaminationSystem.model.entity.user.AuthInfo;
import com.SpringExaminationSystem.repository.user.AuthInfoDao;
import com.SpringExaminationSystem.service.exam.StudentExamService;

@SpringBootTest(classes = CommonTestApplication.class)
class SpringExaminationSystemApplicationTests {

    @Test
    void contextLoads() {

    }

}
