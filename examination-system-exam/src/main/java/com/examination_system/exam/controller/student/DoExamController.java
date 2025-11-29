package com.examination_system.exam.controller.student;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.exam.model.dto.common.ExamLogDTO;
import com.examination_system.exam.model.dto.request.StudentChoiceRequest;
import com.examination_system.exam.model.dto.request.StudentExamCreationRequest;
import com.examination_system.exam.model.dto.response.StudentExamResponse;
import com.examination_system.model.entity.exam.student.StudentExam;
import com.examination_system.exam.model.mapper.StudentExamMapper;
import com.examination_system.exam.service.DoExamService;
import com.examination_system.exam.service.StudentExamService;
import com.examination_system.exam.service.ExamLogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student/exam/do")
@RequiredArgsConstructor
public class DoExamController {
    private final StudentExamService studentExamService;
    private final ExamLogService examLogService;
    private final StudentExamMapper studentExamMapper;
    private final DoExamService doExamService;

    @PostMapping
    public ResponseEntity<StudentExamResponse> createStudentExam(
            @Valid @RequestBody StudentExamCreationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            StudentExam studentExam = studentExamService.getStudentExam(username, request.getExamId());
            StudentExamResponse response = studentExamMapper.toResponse(studentExam);
            // If newly created, ideally 201; service mixes reload/create, so return 200
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/log")
    public ResponseEntity<String> logStudentExam(@Valid @RequestBody ExamLogDTO examLog) {
        try {
            examLogService.createExamLog(examLog.getInfomarion(), examLog.getStudentExamId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Log create sucessfully !");
        } catch (RuntimeException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/submit/{studentExamId}")
    public ResponseEntity<Float> submitExam(@PathVariable Long studentExamId) {
        try {
            StudentExam studentExam = doExamService.submit(studentExamId);
            return ResponseEntity.ok(studentExam.getScore());
        } catch (RuntimeException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/choice/{studentExamId}")
    public ResponseEntity<String> saveChoice(@PathVariable Long studentExamId,
            @RequestBody StudentChoiceRequest request) {
        try {
            doExamService.saveStudentChoice(studentExamId, request.getStudentChoice(), request.isRemove());
            return ResponseEntity.ok("Saved");
        } catch (RuntimeException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
