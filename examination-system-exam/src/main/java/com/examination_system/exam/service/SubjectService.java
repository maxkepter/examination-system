package com.examination_system.exam.service;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.examination_system.exam.model.dto.request.SubjectCreationRequest;
import com.examination_system.common.repository.exam.MajorRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examination_system.common.model.entity.exam.Chapter;
import com.examination_system.common.model.entity.exam.Major;
import com.examination_system.common.model.entity.exam.Subject;
import com.examination_system.exam.model.mapper.SubjectMapper;
import com.examination_system.common.repository.exam.SubjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubjectService {
    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;
    private final MajorRepository majorRepository;

    @Transactional
    public Subject addSubject(SubjectCreationRequest request) {
        Subject subject = toEntity(request);

        subjectRepository.save(subject);

        return subject;
    }

    @Transactional
    public Subject addSubject(Subject subject) {
        Set<Major> majors = subject.getMajors();
        if (majors != null) {
            long numMajorExsit = majorRepository
                    .countByIdIn(subject.getMajors().stream().map((m) -> m.getMajorCode()).toList());
            if (numMajorExsit != subject.getMajors().size()) {
                throw new IllegalArgumentException("One or more majors do not exist");
            }
        }

        // chapter is create new along with subject, so no need to check existence
        return subjectRepository.save(subject);
    }

    public Subject toEntity(SubjectCreationRequest request) {
        Subject subject = subjectMapper.toEntity(request);
        Set<Major> majors = request.getMajorCodes().stream()
                .map(majorCode -> Major.builder().majorCode(majorCode).build()).collect(Collectors.toSet());
        subject.setMajors(majors);
        // Validate and convert chapter codes to Chapter entities
        Set<Chapter> chapters = request.getChapters().stream()
                .map(chapterCode -> Chapter.builder().chapterName(chapterCode).subject(subject).build())
                .collect(Collectors.toSet());
        subject.setChapters(chapters);
        return subject;
    }

    @Transactional(readOnly = true)
    public Subject getSubjectByCode(String subjectCode) {
        Subject subject = subjectRepository.findById(subjectCode)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        // ensure both collections are initialized without multiple bag fetch
        Hibernate.initialize(subject.getMajors());
        Hibernate.initialize(subject.getChapters());
        return subject;
    }

    @Transactional(readOnly = true)
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        subjects.forEach(s -> {
            Hibernate.initialize(s.getMajors());
            Hibernate.initialize(s.getChapters());
        });
        return subjects;
    }

    @Transactional
    public Subject updateSubject(Subject newSubject) {
        if (newSubject == null || newSubject.getSubjectCode() == null) {
            throw new IllegalArgumentException("Subject is required");
        }

        Subject existingSubject = getSubjectByCode(newSubject.getSubjectCode());

        existingSubject.setSubjectName(newSubject.getSubjectName());
        // Update majors (ManyToMany) – simple replace is acceptable
        if (newSubject.getMajors() != null) {
            existingSubject.getMajors().clear();
            existingSubject.getMajors().addAll(newSubject.getMajors());
        }
        // Update chapters (OneToMany orphanRemoval=true): modify collection in place
        if (newSubject.getChapters() != null) {
            // Remove chapters that are no longer present
            Set<String> incomingNames = newSubject.getChapters().stream()
                    .map(Chapter::getChapterName).collect(Collectors.toSet());
            Logger.getAnonymousLogger().log(Level.INFO, "Incoming chapter names: " + incomingNames);
            existingSubject.getChapters().removeIf(ch -> !incomingNames.contains(ch.getChapterName()));
            // Add new chapters that do not exist yet
            Logger.getAnonymousLogger().info("Existing chapters before adding new ones: " +
                    existingSubject.getChapters().stream()
                            .map(Chapter::getChapterName).collect(Collectors.toSet()));
            Set<String> existingNames = existingSubject.getChapters().stream()
                    .map(Chapter::getChapterName).collect(Collectors.toSet());
            newSubject.getChapters().forEach(ch -> {
                if (!existingNames.contains(ch.getChapterName())) {
                    ch.setSubject(existingSubject);
                    existingSubject.getChapters().add(ch);
                }
            });
        }
        return subjectRepository.save(existingSubject);
    }

    @Transactional(readOnly = true)
    public List<Subject> getSubjectsByMajor(String majorCode) {
        List<Subject> list = subjectRepository.findByMajorCode(majorCode);
        list.forEach(s -> {
            Hibernate.initialize(s.getMajors());
            Hibernate.initialize(s.getChapters());
        });
        return list;
    }
}
