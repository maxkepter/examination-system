package com.examination_system.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examination_system.exam.model.dto.common.StudentExamDTO;
import com.examination_system.exam.model.dto.response.StudentExamResponse;
import com.examination_system.exam.model.mapper.StudentExamMapper;
import com.examination_system.common.model.entity.exam.Exam;
import com.examination_system.common.model.entity.exam.student.StudentExam;
import com.examination_system.common.repository.exam.ExamRepository;
import com.examination_system.common.repository.exam.student.StudentExamRepository;

@Service
public class ExamHistoryService {
    @Autowired
    StudentExamRepository studentExamRepository;
    @Autowired
    StudentExamMapper examMapper;
    @Autowired
    ExamRepository examRepository;

    public List<StudentExamDTO> getAll() {
        return studentExamRepository.findAll().stream().map((e) -> examMapper.toDto(e)).toList();
    }

    public List<StudentExamDTO> getByExam(Long examId) {
        // check for exam existence
        Exam exam = examRepository.findActiveById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with id: " + examId));
        return studentExamRepository.findByExam(exam).stream().map((e) -> examMapper.toDto(e)).toList();
    }

    public List<StudentExamDTO> getByUsername(String username) {
        return studentExamRepository.findByUserName(username).stream().map((e) -> examMapper.toDto(e)).toList();
    }

    public List<StudentExamDTO> getByUserId(Long userId) {

        return studentExamRepository.findByUser(userId).stream().map((e) -> examMapper.toDto(e)).toList();
    }

    public StudentExamResponse getStudentExam(String userName, Long studentExamId) {
        StudentExam studentExam = studentExamRepository.findById(studentExamId)
                .orElseThrow(() -> new RuntimeException("Student exam not found with id: " + studentExamId));
        String ownerUserName = studentExam.getUser() != null && studentExam.getUser().getAuthInfo() != null
                ? studentExam.getUser().getAuthInfo().getUserName()
                : null;
        if (ownerUserName == null || !ownerUserName.equals(userName)) {
            throw new RuntimeException("You do not have permission to view this student exam");
        }
        return examMapper.toResponse(studentExam);
    }
}
