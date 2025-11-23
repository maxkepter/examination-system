package com.SpringExaminationSystem.model.entity.log;

import org.springframework.context.annotation.Scope;

import com.SpringExaminationSystem.model.entity.BaseLog;
import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Scope("prototype")
@Entity
@Table(name = "ExamLog")
public class ExamLog extends BaseLog {

    public static final String EXAM_STARTED = "Exam started";
    public static final String EXAM_SUSPENDED = "Exam suspended";
    public static final String EXAM_RESTARTED = "Exam restarted";
    public static final String EXAM_RESUMED = "Exam resumed";
    public static final String EXAM_SUBMITTED = "Exam submitted";

    public ExamLog(String infomation, StudentExam studentExam) {
        super(infomation);
        this.studentExam = studentExam;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer examLogId;

    @ManyToOne
    @JoinColumn(name = "studentExamId", nullable = false)
    private StudentExam studentExam;

}