package com.SpringExaminationSystem.service.exam;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SpringExaminationSystem.model.dto.request.exam.SubjectCreationRequest;
import com.SpringExaminationSystem.model.entity.exam.Chapter;
import com.SpringExaminationSystem.model.entity.exam.Major;
import com.SpringExaminationSystem.model.entity.exam.Subject;
import com.SpringExaminationSystem.model.mapper.exam.SubjectMapper;
import com.SpringExaminationSystem.repository.exam.SubjectDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubjectService {
    private final SubjectMapper subjectMapper;
    private final SubjectDao subjectDao;

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

        subjectDao.save(subject);

        return subject;
    }

    public List<Subject> getAllSubjects() {

        return subjectDao.findAll();
    }
}
