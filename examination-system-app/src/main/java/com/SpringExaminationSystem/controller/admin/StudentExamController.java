package com.SpringExaminationSystem.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringExaminationSystem.model.dto.response.exam.StudentExamResponse;
import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;
import com.SpringExaminationSystem.model.mapper.exam.StudentExamMapper;
import com.SpringExaminationSystem.service.exam.StudentExamService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/exam/student-exam")
@RequiredArgsConstructor
public class StudentExamController {

    private final StudentExamService studentExamService;
    private final StudentExamMapper studentExamMapper;

    @GetMapping("/{studentExamId}")
    public ResponseEntity<StudentExamResponse> getStudentExam(@PathVariable Integer studentExamId) {
        // Get the student exam
        StudentExam studentExam = studentExamService.getStudentExamById(studentExamId);

        // Convert to response DTO and return
        StudentExamResponse response = studentExamMapper.toResponse(studentExam);
        return ResponseEntity.ok(response);
    }

}
