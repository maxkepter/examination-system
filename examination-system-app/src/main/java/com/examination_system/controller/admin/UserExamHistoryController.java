package com.examination_system.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.model.dto.common.StudentExamDTO;
import com.examination_system.service.exam.ExamHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/admin/student-exam")
@RequiredArgsConstructor
public class UserExamHistoryController {
    private final ExamHistoryService examHistoryService;

    @GetMapping()
    public ResponseEntity<List<StudentExamDTO>> getAllExamHistory() {
        return ResponseEntity.ok(examHistoryService.getAll());
    }

    @GetMapping(path = "/exam/{examId}")
    public ResponseEntity<List<StudentExamDTO>> getAllExamHistoryByExam(@PathVariable Integer examId) {
        return ResponseEntity.ok(examHistoryService.getByExam(examId));
    }

    @GetMapping(path = "/student/{userId}")
    public ResponseEntity<List<StudentExamDTO>> getAllExamHistoryByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(examHistoryService.getByUserId(userId));
    }
}
