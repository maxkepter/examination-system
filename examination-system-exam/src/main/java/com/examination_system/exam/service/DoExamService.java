package com.examination_system.exam.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examination_system.common.model.entity.exam.student.Option;
import com.examination_system.common.model.entity.exam.student.QuestionWithOptions;
import com.examination_system.common.model.entity.exam.student.StudentExam;
import com.examination_system.common.repository.exam.student.StudentExamRepository;

@Service
public class DoExamService {
    @Autowired
    StudentExamRepository studentExamRepository;
    @Autowired
    ExamLogService examLogService;

    @Transactional
    public StudentExam submit(Long studentExamId) {
        StudentExam studentExam = studentExamRepository.findActiveById(studentExamId)
                .orElseThrow(() -> new RuntimeException("Not found student exam wtih id: " + studentExamId));

        float score = calculateScore(studentExam.getExamDetail(), studentExam.getStudentChoice());

        studentExam.setScore(score);
        studentExam.setExamStatus(StudentExam.EXAM_DONE);

        examLogService.createExamLog("Submit exam !", studentExamId);

        return studentExam;
    }

    private float calculateScore(List<QuestionWithOptions> examDetail, Map<Long, Set<Long>> studentChoice) {
        if (examDetail == null || examDetail.isEmpty()) {
            throw new IllegalArgumentException("Exam Detail is required");
        }
        int numCorrect = 0;

        for (QuestionWithOptions question : examDetail) {
            if (isChoiceCorrect(question, studentChoice)) {
                numCorrect++;
            }
        }

        return ((float) (numCorrect * 10 / examDetail.size()));

    }

    private boolean isChoiceCorrect(QuestionWithOptions question, Map<Long, Set<Long>> studentChoice) {
        long questionId = question.getQuestionId();
        if (!studentChoice.containsKey(questionId))
            return false;
        // get all optionid correct from question
        Set<Long> correctOptions = question.getOptions().stream()
                .filter(Option::isCorrect)
                .map(Option::getOptionId)
                .collect(Collectors.toSet());
        // get choice from student choice
        Set<Long> chosenOptions = studentChoice.get(questionId);
        return chosenOptions.equals(correctOptions);
    }

    public void saveStudentChoice(Long studentExamId, Map<Long, Set<Long>> studentChoice, boolean isRemove) {
        StudentExam studentExam = studentExamRepository.findActiveById(studentExamId)
                .orElseThrow(() -> new RuntimeException("Not found student exam wtih id: " + studentExamId));

        // validate student choice
        if (studentChoice == null || studentChoice.isEmpty()) {
            return;
        }

        // if current choice is null initialize new one
        Map<Long, Set<Long>> currentChoice = studentExam.getStudentChoice();
        if (currentChoice == null) {
            currentChoice = new HashMap<>();
        }

        for (Map.Entry<Long, Set<Long>> entry : studentChoice.entrySet()) {
            Long questionId = entry.getKey();
            Set<Long> optionIds = entry.getValue();
            if (optionIds == null || optionIds.isEmpty()) {
                continue;
            }

            Set<Long> existing = currentChoice.get(questionId);
            if (isRemove) {
                if (existing != null) {
                    existing.removeAll(optionIds);
                    if (existing.isEmpty()) {
                        currentChoice.remove(questionId);
                    }
                }
            } else {
                if (existing == null) {
                    existing = new HashSet<>();
                    currentChoice.put(questionId, existing);
                }
                existing.addAll(optionIds);
            }
            optionIds.forEach((optionId) -> {
                examLogService.createChoiceLog(questionId, optionId, isRemove, studentExam);
            });
        }
        studentExam.setStudentChoice(currentChoice);
        studentExamRepository.save(studentExam);
    }

}
