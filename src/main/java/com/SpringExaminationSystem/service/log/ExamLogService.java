package com.SpringExaminationSystem.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;
import com.SpringExaminationSystem.model.entity.log.ExamLog;
import com.SpringExaminationSystem.repository.exam.student.StudentExamDao;
import com.SpringExaminationSystem.repository.log.ExamLogDao;

@Service
public class ExamLogService {
    @Autowired
    ExamLogDao examLogDao;
    @Autowired
    StudentExamDao studentExamDao;

    public void createExamLog(String infomation, StudentExam studentExam) {

        ExamLog examLog = new ExamLog(infomation, studentExam);
        examLogDao.save(examLog);
    }

    public void createExamLog(String infomation, Integer studentExamId) {
        StudentExam studentExam = studentExamDao.findActiveById(studentExamId)
                .orElseThrow(() -> new RuntimeException("Student exam not found with id : " + studentExamId));
        ExamLog examLog = new ExamLog(infomation, studentExam);
        examLogDao.save(examLog);
    }

    public void createChoiceLog(Integer questionId, Integer optionId, boolean isRemove, StudentExam studentExam) {
        String information = (isRemove ? "Remove " : "Add ") + " option " + optionId + " in question " + questionId;
        createExamLog(information, studentExam);
    }
}
