package com.examination_system.model.entity.exam;

import org.hibernate.annotations.SQLDelete;
import org.springframework.context.annotation.Scope;

import com.examination_system.model.entity.BaseEntity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "QuestionOption")
@SQLDelete(sql = "update QuestionOption set isActive=0 where optionId=?")
public class QuestionOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer optionId;

    @Column(nullable = false)
    private boolean isCorrect;

    @Lob
    @Column(nullable = false, columnDefinition = "VARCHAR(MAX)")
    private String optionContent;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

}