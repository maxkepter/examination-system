package com.examination_system.exam.controller.admin;

import com.examination_system.exam.model.dto.common.SubjectDto;
import com.examination_system.exam.model.dto.response.SubjectRespone;
import com.examination_system.model.entity.exam.Subject;

import jakarta.validation.Valid;

import com.examination_system.exam.model.mapper.SubjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examination_system.exam.model.dto.request.SubjectCreationRequest;
import com.examination_system.exam.service.SubjectService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/admin/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @PostMapping
    public ResponseEntity<SubjectRespone> addSubject(@Valid @RequestBody SubjectCreationRequest request) {
        try {
            Subject subject = subjectService.addSubject(request);
            SubjectRespone body = subjectMapper.toResponse(subject);
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SubjectRespone>> getAllSubjects() {
        List<SubjectRespone> subjects = subjectService.getAllSubjects().stream()
                .map(subjectMapper::toResponse)
                .toList();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/{subjectCode}")
    public ResponseEntity<SubjectRespone> getSubjectById(@PathVariable String subjectCode) {
        try {
            Subject subject = subjectService.getSubjectByCode(subjectCode);
            return ResponseEntity.ok(subjectMapper.toResponse(subject));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/major/{majorCode}")
    public ResponseEntity<List<SubjectRespone>> getSubjectsByMajor(@PathVariable String majorCode) {
        List<SubjectRespone> subjects = subjectService.getSubjectsByMajor(majorCode).stream()
                .map(subjectMapper::toResponse)
                .toList();
        return ResponseEntity.ok(subjects);
    }

    @PatchMapping("/{subjectCode}")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable String subjectCode,
            @Valid @RequestBody SubjectCreationRequest request) {
        try {
            Subject subject = subjectService.toEntity(request);
            subject.setSubjectCode(subjectCode);
            Subject updated = subjectService.updateSubject(subject);
            return ResponseEntity.ok(subjectMapper.toDto(updated));
        } catch (IllegalArgumentException ex) {
            // If not found or invalid, map to 404 or 400 depending on message
            String msg = ex.getMessage() != null ? ex.getMessage() : "";
            if (msg.toLowerCase().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
