package com.examination_system.controller.student;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.model.dto.common.StudentExamDTO;
import com.examination_system.model.dto.response.exam.StudentExamResponse;
import com.examination_system.service.exam.ExamHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/student/exam")
@RequiredArgsConstructor
public class UserExamController {
    private final ExamHistoryService examHistoryService;

    @GetMapping(path = "/history")
    public ResponseEntity<List<StudentExamDTO>> getExamHistory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(examHistoryService.getByUsername(username));
    }

    @GetMapping(path = "/view/{studentExamId}")
    public ResponseEntity<StudentExamResponse> getStudentExam(@PathVariable Integer studentExamId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(examHistoryService.getStudentExam(username, studentExamId));
    }
}