package com.examination_system.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examination_system.common.model.entity.exam.student.StudentExam;
import com.examination_system.common.model.entity.log.ExamLog;
import com.examination_system.common.repository.exam.student.StudentExamRepository;
import com.examination_system.common.repository.log.ExamLogRepository;

import java.util.List;

@Service
public class ExamLogService {
    @Autowired
    ExamLogRepository examLogRepository;
    @Autowired
    StudentExamRepository studentExamRepository;

    public void createExamLog(String infomation, StudentExam studentExam) {

        ExamLog examLog = new ExamLog(infomation, studentExam);
        examLogRepository.save(examLog);
    }

    public void createExamLog(String infomation, Long studentExamId) {
        StudentExam studentExam = studentExamRepository.findActiveById(studentExamId)
                .orElseThrow(() -> new RuntimeException("Student exam not found with id : " + studentExamId));
        ExamLog examLog = new ExamLog(infomation, studentExam);
        examLogRepository.save(examLog);
    }

    public void createChoiceLog(Long questionId, Long optionId, boolean isRemove, StudentExam studentExam) {
        String information = (isRemove ? "Remove " : "Add ") + " option " + optionId + " in question " + questionId;
        createExamLog(information, studentExam);
    }

    public List<ExamLog> getExamLogsByStudentExamId(Long studentExamId) {
        // Verify student exam exists
        studentExamRepository.findById(studentExamId)
                .orElseThrow(() -> new RuntimeException("Student exam not found with id: " + studentExamId));
        
        return examLogRepository.findByStudentExamId(studentExamId);
    }
}
