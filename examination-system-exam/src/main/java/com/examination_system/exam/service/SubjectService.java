package com.examination_system.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examination_system.model.dto.request.exam.SubjectCreationRequest;
import com.examination_system.model.entity.exam.Chapter;
import com.examination_system.model.entity.exam.Major;
import com.examination_system.model.entity.exam.Subject;
import com.examination_system.model.mapper.exam.SubjectMapper;
import com.examination_system.repository.exam.SubjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubjectService {
    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;

    public Subject addSubject(SubjectCreationRequest request) {
        Subject subject = subjectMapper.toEntity(request);
        // Convert major codes to Major entities
        List<Major> majors = request.getMajorCodes().stream()
                .map(majorCode -> Major.builder().majorCode(majorCode).build())
                .toList();
        subject.setMajors(majors);
        // Validate and convert chapter codes to Chapter entities
        List<Chapter> chapters = request.getChapters().stream()
                .map(chapterCode -> Chapter.builder().chapterName(chapterCode).subject(subject).build()).toList();
        subject.setChapters(chapters);

        subjectRepository.save(subject);

        return subject;
    }

    public List<Subject> getAllSubjects() {

        return subjectRepository.findAll();
    }
}
