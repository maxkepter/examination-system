package com.examination_system.exam.controller.student;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.exam.model.dto.common.StudentExamDTO;
import com.examination_system.exam.model.dto.response.StudentExamResponse;
import com.examination_system.exam.service.ExamHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/student/exam")
@RequiredArgsConstructor
public class UserExamController {
    private final ExamHistoryService examHistoryService;

    @GetMapping(path = "/history")
    public ResponseEntity<List<StudentExamDTO>> getExamHistory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<StudentExamDTO> list = examHistoryService.getByUsername(username);
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/view/{studentExamId}")
    public ResponseEntity<StudentExamResponse> getStudentExam(@PathVariable Long studentExamId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            StudentExamResponse resp = examHistoryService.getStudentExam(username, studentExamId);
            return ResponseEntity.ok(resp);
        } catch (RuntimeException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (msg.contains("permission")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
