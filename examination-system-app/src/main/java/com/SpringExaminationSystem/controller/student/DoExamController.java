package com.SpringExaminationSystem.controller.student;

import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringExaminationSystem.model.dto.common.ExamLogDTO;
import com.SpringExaminationSystem.model.dto.request.exam.StudentChoiceRequest;
import com.SpringExaminationSystem.model.dto.request.exam.StudentExamCreationRequest;
import com.SpringExaminationSystem.model.dto.response.exam.StudentExamResponse;
import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;
import com.SpringExaminationSystem.model.mapper.exam.StudentExamMapper;
import com.SpringExaminationSystem.service.exam.DoExamService;
import com.SpringExaminationSystem.service.exam.StudentExamService;
import com.SpringExaminationSystem.service.log.ExamLogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student/exam/do")
@RequiredArgsConstructor
public class DoExamController {
    private final StudentExamService studentExamService;
    private final ExamLogService examLogService;
    private final StudentExamMapper studentExamMapper;
    private final DoExamService doExamService;

    @PostMapping
    public ResponseEntity<StudentExamResponse> createStudentExam(
            @Valid @RequestBody StudentExamCreationRequest request) {
        // Get username from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Create student exam using the service
        StudentExam studentExam = studentExamService.getStudentExam(username, request.getExamId());

        // Convert to response DTO and return
        StudentExamResponse response = studentExamMapper.toResponse(studentExam);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/log")
    public ResponseEntity<String> logStudentExam(@Valid @RequestBody ExamLogDTO examLog) {
        examLogService.createExamLog(examLog.getInfomarion(), examLog.getStudentExamId());
        return ResponseEntity.ok("Log create sucessfully !");
    }

    @PostMapping("/submit/{studentExamId}")
    public ResponseEntity<Float> submitExam(@PathVariable Integer studentExamId) {
        StudentExam studentExam = doExamService.submit(studentExamId);
        return ResponseEntity.ok(studentExam.getScore());
    }

    @PostMapping("/choice/{studentExamId}")
    public ResponseEntity<String> saveChoice(@PathVariable Integer studentExamId,
            @RequestBody StudentChoiceRequest request) {
        doExamService.saveStudentChoice(studentExamId, request.getStudentChoice(), request.isRemove());
        return ResponseEntity.ok("Saved");
    }

}
