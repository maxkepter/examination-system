package com.examination_system.exam.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.model.dto.response.exam.StudentExamResponse;
import com.examination_system.model.entity.exam.student.StudentExam;
import com.examination_system.model.mapper.exam.StudentExamMapper;
import com.examination_system.exam.service.StudentExamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/exam/student-exam")
@RequiredArgsConstructor
public class StudentExamController {

    private final StudentExamService studentExamService;
    private final StudentExamMapper studentExamMapper;

    @GetMapping("/{studentExamId}")
    public ResponseEntity<StudentExamResponse> getStudentExam(@PathVariable Long studentExamId) {
        StudentExam studentExam = studentExamService.getStudentExamById(studentExamId);
        StudentExamResponse response = studentExamMapper.toResponse(studentExam);
        return ResponseEntity.ok(response);
    }

}
