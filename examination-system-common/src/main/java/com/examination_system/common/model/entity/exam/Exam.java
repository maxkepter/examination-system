package com.examination_system.model.entity.exam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.springframework.context.annotation.Scope;

import com.examination_system.core.model.entity.BaseEntity;
import com.examination_system.model.entity.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Scope("prototype")
@Entity
@Table(name = "Exam")
@SQLDelete(sql = "update Exam set isActive=0 where examId=?")
public class Exam extends BaseEntity {
    public static final String EXAM_ID = "examId";
    public static final String EXAM_DURATION = "duration";
    public static final String EXAM_CODE = "examCode";
    public static final String EXAM_NAME = "examName";

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long examId;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private LocalDateTime examDate;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false, unique = true, length = 50)
    private String examCode;

    @Column(nullable = false, length = 100)
    private String examName;

    @ManyToMany(targetEntity = Question.class)
    @JoinTable(name = "Exam_Question", joinColumns = @JoinColumn(name = "examID", referencedColumnName = "examID"), inverseJoinColumns = @JoinColumn(name = "questionID", referencedColumnName = "questionID"))
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}