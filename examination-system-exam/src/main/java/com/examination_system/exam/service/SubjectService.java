package com.examination_system.exam.service;

import java.util.List;

import com.examination_system.repository.exam.ChapterRepository;
import com.examination_system.repository.exam.MajorRepository;
import org.springframework.stereotype.Service;

import com.examination_system.exam.model.dto.request.SubjectCreationRequest;
import com.examination_system.model.entity.exam.Chapter;
import com.examination_system.model.entity.exam.Major;
import com.examination_system.model.entity.exam.Subject;
import com.examination_system.exam.model.mapper.SubjectMapper;
import com.examination_system.repository.exam.SubjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubjectService {
    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;
    private final MajorRepository majorRepository;
    private final ChapterRepository chapterRepository;

    public Subject addSubject(SubjectCreationRequest request) {
        Subject subject = toEntity(request);

        subjectRepository.save(subject);

        return subject;
    }

    public Subject addSubject(Subject subject) {
        List<Major> majors = subject.getMajors();
        if (majors != null) {
            long numMajorExsit = majorRepository.countByIdIn(subject.getMajors().stream().map((m) -> m.getMajorCode()).toList());
            if (numMajorExsit != subject.getMajors().size()) {
                throw new IllegalArgumentException("One or more majors do not exist");
            }
        }

        // chapter is create new along with subject, so no need to check existence
        return subjectRepository.save(subject);
    }

    public Subject toEntity(SubjectCreationRequest request) {
        Subject subject = subjectMapper.toEntity(request);
        List<Major> majors = request.getMajorCodes().stream()
                .map(majorCode -> Major.builder().majorCode(majorCode).build())
                .toList();
        subject.setMajors(majors);
        // Validate and convert chapter codes to Chapter entities
        List<Chapter> chapters = request.getChapters().stream()
                .map(chapterCode -> Chapter.builder().chapterName(chapterCode).subject(subject).build()).toList();
        subject.setChapters(chapters);
        return subject;
    }

    public Subject getSubjectByCode(String subjectCode) {
        return subjectRepository.findById(subjectCode)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
    }

    public List<Subject> getAllSubjects() {

        return subjectRepository.findAll();
    }

    public Subject updateSubject(Subject newSubject) {
        if (newSubject == null || newSubject.getSubjectCode() == null) {
            throw new IllegalArgumentException("Subject is required");
        }

        Subject existingSubject = getSubjectByCode(newSubject.getSubjectCode());

        existingSubject.setSubjectName(newSubject.getSubjectName());
        if (newSubject.getMajors() != null && !newSubject.getMajors().isEmpty()) {
            existingSubject.setMajors(newSubject.getMajors());
        }
        if (newSubject.getChapters() != null && !newSubject.getChapters().isEmpty()) {
            existingSubject.setChapters(newSubject.getChapters());
        }
        return subjectRepository.save(existingSubject);
    }

    public List<Subject> getSubjectsByMajor(String majorCode) {
        return subjectRepository.findByMajorCode(majorCode);
    }
}
