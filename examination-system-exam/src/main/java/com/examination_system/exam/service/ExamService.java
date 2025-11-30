//package com.examination_system.exam.service;
//
//import com.examination_system.common.model.entity.exam.Exam;
//import com.examination_system.common.model.entity.exam.Question;
//import com.examination_system.common.repository.exam.ExamRepository;
//import com.examination_system.common.repository.exam.QuestionRepository;
//import com.examination_system.exam.model.dto.common.QuestionTemplate;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//class ExamService {
//    private final ExamRepository examRepository;
//    private final QuestionRepository questionRepository;
//    public Exam createExam(int duration, LocalDate examDate, LocalDate deadline, String examName, List<QuestionTemplate> questionTemplates) {
//
//        return exam;
//    }
//
//    private List<Question> getQuestionsFromTemplates(List<@Valid QuestionTemplate> questionTemplates) {
//        List<Question> questions = new ArrayList<>();
//        for (QuestionTemplate questionTemplate : questionTemplates) {
//            Specification specification = Specification.where(null);
//            if (questionTemplate.getDifficulty() != null) {
//                specification = specification.and((root, query, criteriaBuilder) ->
//                        criteriaBuilder.equal(root.get("difficulty"), questionTemplate.getDifficulty()));
//            }
//            if (questionTemplate.getChapterId() != null) {
//                specification = specification.and((root, query, criteriaBuilder) ->
//                        criteriaBuilder.equal(root.get("chapter").get("chapterId"), questionTemplate.getChapterId()));
//            }
//            if (questionTemplate.getSubjectCode() != null) {
//                specification = specification.and((root, query, criteriaBuilder) ->
//                        criteriaBuilder.equal(root.get("chapter").get("subject").get("subjectCode"), questionTemplate.getSubjectCode()));
//            }
//            List<Question> questionList=questionRepository.findAll()
//        }
//        return null;
//    }
//}
