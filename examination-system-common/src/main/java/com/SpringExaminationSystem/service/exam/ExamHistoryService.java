package com.SpringExaminationSystem.service.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringExaminationSystem.model.dto.common.StudentExamDTO;
import com.SpringExaminationSystem.model.dto.response.exam.StudentExamResponse;
import com.SpringExaminationSystem.model.entity.exam.Exam;
import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;
import com.SpringExaminationSystem.model.mapper.exam.StudentExamMapper;
import com.SpringExaminationSystem.repository.exam.ExamDao;
import com.SpringExaminationSystem.repository.exam.student.StudentExamDao;

@Service
public class ExamHistoryService {
    @Autowired
    StudentExamDao studentExamDao;
    @Autowired
    StudentExamMapper examMapper;
    @Autowired
    ExamDao examDao;

    public List<StudentExamDTO> getAll() {
        return studentExamDao.findAll().stream().map((e) -> examMapper.toDto(e)).toList();
    }

    public List<StudentExamDTO> getByExam(Integer examId) {
        // check for exam existence
        Exam exam = examDao.findActiveById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
        return studentExamDao.findByExam(exam).stream().map((e) -> examMapper.toDto(e)).toList();
    }

    public List<StudentExamDTO> getByUsername(String username) {
        return studentExamDao.findByUserName(username).stream().map((e) -> examMapper.toDto(e)).toList();
    }

    public List<StudentExamDTO> getByUserId(Integer userId) {
        return studentExamDao.findByUser(userId).stream().map((e) -> examMapper.toDto(e)).toList();
    }

    public StudentExamResponse getStudentExam(String userName, Integer studentExamId) {
        StudentExam studentExam = studentExamDao.findById(studentExamId)
                .orElseThrow(() -> new RuntimeException("Student exam not found with id: " + studentExamId));
        if (studentExam.getCreatedBy() == null || !studentExam.getCreatedBy().equals(userName)) {
            throw new RuntimeException("You do not have permission to view this student exam");
        }
        return examMapper.toResponse(studentExam);
    }
}
