package com.SpringExaminationSystem.service.exam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringExaminationSystem.model.entity.exam.Exam;
import com.SpringExaminationSystem.model.entity.exam.Question;
import com.SpringExaminationSystem.model.entity.exam.student.QuestionWithOptions;
import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;
import com.SpringExaminationSystem.model.entity.log.ExamLog;
import com.SpringExaminationSystem.model.entity.user.User;
import com.SpringExaminationSystem.repository.exam.ExamDao;
import com.SpringExaminationSystem.repository.exam.student.StudentExamDao;
import com.SpringExaminationSystem.repository.user.AuthInfoDao;
import com.SpringExaminationSystem.service.log.ExamLogService;

@Service
public class StudentExamService {
    @Autowired
    ExamDao examDao;
    @Autowired
    AuthInfoDao authInfoDao;
    @Autowired
    StudentExamDao studentExamDao;
    @Autowired
    ExamLogService examLogService;
    @Autowired
    DoExamService doExamService;

    @Transactional
    public StudentExam getStudentExam(String userName, Integer examId) {
        User user = authInfoDao.findByUserName(userName).getUser();
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

        Exam exam = examDao.findActiveByIdWithQuestions(examId)
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

        studentExamDao.save(studentExam);
        examLogService.createExamLog(ExamLog.EXAM_STARTED, studentExam);
        return studentExam;
    }

    @Transactional(readOnly = true)
    public StudentExam reloadStudentExam(User user, Integer examId) throws IllegalArgumentException {
        StudentExam studentExam = studentExamDao
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
        return studentExamDao.findById(studentExamId)
                .orElseThrow(() -> new RuntimeException("Student exam not found with id: " + studentExamId));
    }
}
