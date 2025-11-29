package com.examination_system.exam.controller.admin;

import com.examination_system.exam.model.dto.common.SubjectDto;
import com.examination_system.exam.model.dto.response.SubjectRespone;
import com.examination_system.model.entity.exam.Subject;
import com.examination_system.exam.model.mapper.SubjectMapper;
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
    public void addSubject(@RequestBody SubjectCreationRequest request) {
        subjectService.addSubject(request);
    }
    @GetMapping
    public ResponseEntity<?> getAllSubjects() {
        List<SubjectRespone> subjects= subjectService.getAllSubjects().stream()
                .map(subjectMapper::toResponse)
                .toList();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/{subjectCode}" )
    public ResponseEntity<?> getSubjectById(@PathVariable String subjectCode) {
        return ResponseEntity.ok(subjectMapper.toResponse(subjectService.getSubjectByCode(subjectCode)));
    }

    @GetMapping("/major/{majorCode}" )
    public ResponseEntity<?> getSubjectsByMajor(@PathVariable String majorCode) {
        List<SubjectRespone> subjects= subjectService.getSubjectsByMajor(majorCode).stream()
                .map(subjectMapper::toResponse)
                .toList();
        return ResponseEntity.ok(subjects);
    }

    @PatchMapping("/{subjectCode}" )
    public ResponseEntity<?> updateSubject(@PathVariable String subjectCode, @RequestBody SubjectCreationRequest request) {
     Subject subject=   subjectService.toEntity(request);
        subject=subjectService.updateSubject(subject);
        return ResponseEntity.ok(subjectMapper.toDto(subject));
    }

}
