package com.examination_system.exam.service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examination_system.common.model.entity.exam.Chapter;
import com.examination_system.common.model.entity.exam.Question;
import com.examination_system.common.model.entity.exam.QuestionOption;
import com.examination_system.common.model.entity.exam.Subject;

import com.examination_system.common.repository.exam.ChapterRepository;
import com.examination_system.common.repository.exam.QuestionRepository;
import com.examination_system.common.repository.exam.SubjectRepository;
import com.examination_system.exam.model.dto.common.QuestionDto;
import com.examination_system.exam.model.dto.request.QuestionCountRequest;
import com.examination_system.exam.model.dto.response.QuestionCountResponse;
import com.examination_system.exam.model.mapper.QuestionMapper;
import com.examination_system.exam.model.mapper.QuestionOptionMapper;
import com.examination_system.common.repository.exam.QuestionOptionRepository;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionMapper questionMapper;
    private final QuestionOptionMapper questionOptionMapper;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final SubjectRepository subjectRepository;
    private final ChapterRepository chapterRepository;

    @Transactional
    public void createQuestion(QuestionDto questionDTO) {
        Question question = Question.builder()
                .questionContent(questionDTO.getQuestionContent())
                .difficulty(questionDTO.getDifficulty())
                .build();

        if (questionDTO.getChapterId() != null) {
            Chapter chapter = new Chapter();
            chapter.setChapterId(questionDTO.getChapterId());
            question.setChapter(chapter);
        }

        if (questionDTO.getOptions() != null && !questionDTO.getOptions().isEmpty()) {

            List<QuestionOption> optionEntities = questionDTO.getOptions().stream()
                    .map(optionDTO -> {
                        QuestionOption option = questionOptionMapper.toNewEntity(optionDTO);
                        option.setQuestion(question);
                        option.setCorrect(optionDTO.getIsCorrect());
                        return option;
                    })
                    .toList();
            question.setOptions(optionEntities);
        }

        questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        return questionRepository.findAllActiveWithOptions();
    }

    // Filter questions by optional difficulty, chapterId, or subjectCode
    @Transactional(readOnly = true)
    public List<Question> filterQuestions(Integer difficulty, Long chapterId, String subjectCode) {
        Specification<Question> spec = Specification.where(null);

        if (difficulty != null) {
            spec = spec.and((root, cq, cb) -> cb.equal(root.get("difficulty"), difficulty));
        }
        if (chapterId != null) {
            spec = spec.and((root, cq, cb) -> cb.equal(root.join("chapter").get("chapterId"), chapterId));
        }
        if (subjectCode != null && !subjectCode.isEmpty()) {
            spec = spec.and((root, cq, cb) -> {
                // 1. Access nested subjectCode via joins
                var subjectCodePath = root.join("chapter").join("subject").<String>get("subjectCode");

                return cb.like(cb.lower(subjectCodePath), "%" + subjectCode.toLowerCase() + "%");
            });
        }
        // Ensure not soft-deleted (isActive = true)
        spec = spec.and((root, cq, cb) -> cb.isTrue(root.get("isActive")));

        List<Question> questions = questionRepository.findAll(spec);
        // Trigger lazy loading of options within transaction
        questions.forEach(q -> q.getOptions().size());
        return questions;
    }

    @Transactional
    public Question updateQuestion(Question updatedQuestion) {
        if (!questionRepository.existsById(updatedQuestion.getQuestionId())) {
            throw new IllegalArgumentException(
                    "Question with ID " + updatedQuestion.getQuestionId() + " does not exist.");
        }

        // Fetch existing question with options
        Question existingQuestion = questionRepository.findById(updatedQuestion.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        // Update basic fields
        existingQuestion.setQuestionContent(updatedQuestion.getQuestionContent());
        existingQuestion.setDifficulty(updatedQuestion.getDifficulty());
        existingQuestion.setChapter(updatedQuestion.getChapter());

        // Clear old options and add new ones
        existingQuestion.getOptions().clear();
        if (updatedQuestion.getOptions() != null && !updatedQuestion.getOptions().isEmpty()) {
            updatedQuestion.getOptions().forEach(option -> {
                option.setQuestion(existingQuestion);
                existingQuestion.getOptions().add(option);
            });
        }

        return questionRepository.save(existingQuestion);
    }

    @Transactional
    public void deleteQuestion(long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new IllegalArgumentException("Question with ID " + questionId + " does not exist.");
        }
        questionRepository.deleteById(questionId);
    }

    @Transactional(readOnly = true)
    public QuestionCountResponse countAvailableQuestions(QuestionCountRequest request) {
        Specification<Question> spec = Specification.where(null);
        
        // Filter by active status
        spec = spec.and((root, query, criteriaBuilder) -> 
            criteriaBuilder.isTrue(root.get("isActive")));
        
        // Filter by subject (through chapter)
        if (request.getSubjectCode() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("chapter").get("subject").get("subjectCode"), request.getSubjectCode()));
        }
        
        // Filter by chapter
        if (request.getChapterId() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("chapter").get("chapterId"), request.getChapterId()));
        }
        
        // Filter by difficulty
        if (request.getDifficulty() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("difficulty"), request.getDifficulty()));
        }
        
        long count = questionRepository.count(spec);
        
        // Build response with additional info
        QuestionCountResponse.QuestionCountResponseBuilder responseBuilder = QuestionCountResponse.builder()
                .availableQuestions(count)
                .difficulty(request.getDifficulty());
        
        // Get subject info if provided
        if (request.getSubjectCode() != null) {
            Subject subject = subjectRepository.findById(request.getSubjectCode())
                    .orElse(null);
            if (subject != null) {
                responseBuilder.subjectCode(subject.getSubjectCode())
                        .subjectName(subject.getSubjectName());
            }
        }
        
        // Get chapter info if provided
        if (request.getChapterId() != null) {
            Chapter chapter = chapterRepository.findById(request.getChapterId())
                    .orElse(null);
            if (chapter != null) {
                responseBuilder.chapterId(chapter.getChapterId())
                        .chapterName(chapter.getChapterName());
                
                // If subject not provided but chapter is, get subject from chapter
                if (request.getSubjectCode() == null && chapter.getSubject() != null) {
                    responseBuilder.subjectCode(chapter.getSubject().getSubjectCode())
                            .subjectName(chapter.getSubject().getSubjectName());
                }
            }
        }
        
        return responseBuilder.build();
    }

}
