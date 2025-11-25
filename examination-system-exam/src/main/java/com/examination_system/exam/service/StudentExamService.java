package com.examination_system.exam.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examination_system.model.entity.exam.Exam;
import com.examination_system.model.entity.exam.Question;
import com.examination_system.model.entity.exam.student.QuestionWithOptions;
import com.examination_system.model.entity.exam.student.StudentExam;
import com.examination_system.model.entity.log.ExamLog;
import com.examination_system.model.entity.user.User;
import com.examination_system.repository.exam.ExamRepository;
import com.examination_system.repository.exam.student.StudentExamRepository;
import com.examination_system.repository.user.AuthInfoRepository;

@Service
public class StudentExamService {
    @Autowired
    ExamRepository examRepository;
    @Autowired
    AuthInfoRepository authInfoRepository;
    @Autowired
    StudentExamRepository studentExamRepository;
    @Autowired
    ExamLogService examLogService;
    @Autowired
    DoExamService doExamService;

    @Transactional
    public StudentExam getStudentExam(String userName, Integer examId) {
        User user = authInfoRepository.findByUserName(userName).getUser();
        StudentExam studentExam = null;
        try {
            studentExam = reloadStudentExam(user, examId);
        } catch (IllegalArgumentException e) {
            studentExam = createStudentExam(user, examId);
        }

        return studentExam;
    }

    @Transactional
    public StudentExam createStudentExam(User user, Integer examId) {

        Exam exam = examRepository.findActiveByIdWithQuestions(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
        List<Question> questions = exam.getQuestions();
        List<QuestionWithOptions> questionWithOptions = QuestionWithOptions.convertFromEntities(questions);
        QuestionWithOptions.randomQuestion(questionWithOptions);
        StudentExam studentExam = StudentExam.builder()
                .examStatus(StudentExam.EXAM_DOING)
                .score(0)
                .submitTime(LocalDateTime.now())
                .startTime(LocalDateTime.now())
                .examDetail(questionWithOptions)
                .studentChoice(new HashMap<>())
                .exam(exam)
                .user(user)
                .build();

        studentExamRepository.save(studentExam);
        examLogService.createExamLog(ExamLog.EXAM_STARTED, studentExam);
        return studentExam;
    }

    @Transactional(readOnly = true)
    public StudentExam reloadStudentExam(User user, Integer examId) throws IllegalArgumentException {
        StudentExam studentExam = studentExamRepository
                .findByUserAndExamAndStatus(user.getUserId(), examId, StudentExam.EXAM_DOING)
                .orElseThrow(() -> new IllegalArgumentException("Student exam not found for examId: " + examId));

        int duration = studentExam.getExam().getDuration();
        LocalDate deadline = studentExam.getExam().getDeadline();
        LocalDateTime starTime = studentExam.getStartTime();

        // if over deadline or run out time, submit exam
        if (deadline.isBefore(LocalDate.now()) || starTime.plusMinutes(duration).isBefore(LocalDateTime.now())) {
            doExamService.submit(examId);
            throw new RuntimeException("Exam run out of time");
        }
        examLogService.createExamLog(ExamLog.EXAM_RESTARTED, studentExam);

        return studentExam;
    }

    @Transactional(readOnly = true)
    public StudentExam getStudentExamById(Integer studentExamId) {
        return studentExamRepository.findById(studentExamId)
                .orElseThrow(() -> new RuntimeException("Student exam not found with id: " + studentExamId));
    }
}
