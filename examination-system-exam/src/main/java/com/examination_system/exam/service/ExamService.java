package com.examination_system.exam.service;

import com.examination_system.common.model.entity.exam.Exam;
import com.examination_system.common.model.entity.exam.Question;
import com.examination_system.common.model.entity.exam.Subject;
import com.examination_system.common.repository.exam.ExamRepository;
import com.examination_system.common.repository.exam.QuestionRepository;
import com.examination_system.common.repository.exam.SubjectRepository;
import com.examination_system.exam.model.dto.common.ExamDto;
import com.examination_system.exam.model.dto.common.QuestionTemplate;
import com.examination_system.exam.model.dto.request.ExamCreationRequest;
import com.examination_system.exam.model.dto.response.ExamResponse;
import com.examination_system.exam.model.mapper.ExamMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final SubjectRepository subjectRepository;
    private final ExamMapper examMapper;

    @Transactional
    public ExamResponse createExam(ExamCreationRequest request) {
        // Get subject
        Subject subject = subjectRepository.findById(request.getSubjectCode())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        // Get questions based on templates
        List<Question> questions = getQuestionsFromTemplates(request.getQuestionTemplates());
        Collections.shuffle(questions);
        
        // Build exam
        Exam exam = Exam.builder()
                .duration(request.getDuration())
                .examDate(request.getExamDate())
                .deadline(request.getDeadline())
                .examName(request.getExamName())
                .examCode(generateExamCode())
                .subject(subject)
                .questions(questions)
                .build();
        
        // Save exam
        Exam savedExam = examRepository.save(exam);
        
        // Convert to response
        return examMapper.toResponse(savedExam);
    }

    private String generateExamCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder examCode = new StringBuilder();
        examCode.append("EXAM-").append(DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()));
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            examCode.append(chars.charAt(index));
        }
        return examCode.toString();
    }

    private List<Question> getQuestionsFromTemplates(List<@Valid QuestionTemplate> questionTemplates) {
        List<Question> questions = new ArrayList<>();
        
        for (QuestionTemplate template : questionTemplates) {
            Specification<Question> spec = Specification.where(null);
            
            // Filter by difficulty
            if (template.getDifficulty() != null) {
                spec = spec.and((root, query, criteriaBuilder) -> 
                    criteriaBuilder.equal(root.get("difficulty"), template.getDifficulty()));
            }
            
            // Filter by chapter
            if (template.getChapterId() != null) {
                spec = spec.and((root, query, criteriaBuilder) -> 
                    criteriaBuilder.equal(root.get("chapter").get("chapterId"), template.getChapterId()));
            }
            
            // Filter active questions
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.isTrue(root.get("isActive")));
            
            // Get questions matching criteria
            List<Question> matchingQuestions = questionRepository.findAll(spec);
            
            // Shuffle and take the requested amount
            Collections.shuffle(matchingQuestions);
            int amount = Math.min(template.getAmount(), matchingQuestions.size());
            questions.addAll(matchingQuestions.subList(0, amount));
        }
        
        return questions;
    }

    @Transactional(readOnly = true)
    public List<ExamResponse> getAllExams() {
        List<Exam> exams = examRepository.findAllActive();
        return exams.stream()
                .map(examMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExamResponse getExamById(Long examId) {
        Exam exam = examRepository.findActiveByIdWithQuestions(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        return examMapper.toResponse(exam);
    }

    @Transactional(readOnly = true)
    public ExamResponse getExamByCode(String examCode) {
        Exam exam = examRepository.findExamByCode(examCode)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        return examMapper.toResponse(exam);
    }

    @Transactional
    public ExamResponse updateExam(Long examId, ExamDto examDto) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        
        // Update subject if provided
        if (examDto.getSubjectCode() != null) {
            Subject subject = subjectRepository.findById(examDto.getSubjectCode())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            exam.setSubject(subject);
        }
        
        // Update basic fields
        exam.setDuration(examDto.getDuration());
        exam.setExamDate(examDto.getExamDate());
        exam.setDeadline(examDto.getDeadline());
        exam.setExamName(examDto.getExamName());
        
        // Update questions if templates provided
        if (examDto.getQuestionTemplates() != null && !examDto.getQuestionTemplates().isEmpty()) {
            List<Question> questions = getQuestionsFromTemplates(examDto.getQuestionTemplates());
            Collections.shuffle(questions);
            exam.setQuestions(questions);
        }
        
        Exam updatedExam = examRepository.save(exam);
        return examMapper.toResponse(updatedExam);
    }

    @Transactional
    public void deleteExam(Long examId) {
        if (!examRepository.existsById(examId)) {
            throw new RuntimeException("Exam not found");
        }
        examRepository.deleteById(examId);
    }

    @Transactional(readOnly = true)
    public List<ExamResponse> getExamsBySubject(String subjectCode) {
        List<Exam> exams = examRepository.findBySubjectCode(subjectCode);
        return exams.stream()
                .map(examMapper::toResponse)
                .collect(Collectors.toList());
    }
}
