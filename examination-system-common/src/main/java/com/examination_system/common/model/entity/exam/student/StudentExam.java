package com.examination_system.common.model.entity.exam.student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;

import com.examination_system.common.model.converter.ExamDetailConverter;
import com.examination_system.common.model.converter.StudentChoiceConverter;
import com.examination_system.core.model.entity.BaseEntity;
import com.examination_system.common.model.entity.exam.Exam;
import com.examination_system.common.model.entity.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "StudentExam")
@SQLDelete(sql = "update StudentExam set isActive=0 where studentExamId=?")
public class StudentExam extends BaseEntity {
    public static final int EXAM_CLOSED = 0;
    public static final int EXAM_DONE = 1;
    public static final int EXAM_DOING = 2;
    public static final int EXAM_SUSPENDED = 3;
    public static final String[] EXAM_STATUS_INFO = { "Exam closed", "Exam done", "Exam doing", "Exam suspended" };
    public static final String EXAM_STATUS = "examStatus";
    public static final String SCORE = "score";
    public static final String SUBMIT_TIME = "submitTime";
    public static final String START_TIME = "startTime";
    public static final String EXAM_DETAIL = "examDetail";
    public static final String STUDENT_CHOICE = "studentChoice";
    public static final String EXAM = "exam";
    public static final String STUDENT = "student";
    public static final String[] ATTRIBUTE_NAME = {
            EXAM_STATUS,
            SCORE,
            SUBMIT_TIME,
            START_TIME,
            EXAM_DETAIL,
            STUDENT_CHOICE,
            EXAM,
            STUDENT
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentExamId;

    @Column(nullable = false)
    private int examStatus;

    @Column(nullable = false)
    private float score;

    @Column(nullable = false)
    private LocalDateTime submitTime;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Lob
    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    @Convert(converter = ExamDetailConverter.class)
    private List<QuestionWithOptions> examDetail;

    @Lob
    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    @Convert(converter = StudentChoiceConverter.class)
    private Map<Long, Set<Long>> studentChoice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ExamId", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

}