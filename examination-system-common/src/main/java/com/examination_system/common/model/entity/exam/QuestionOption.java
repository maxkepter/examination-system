package com.examination_system.common.model.entity.exam;

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
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long optionId;

    @Column(nullable = false)
    private boolean isCorrect;

    @Lob
    @Column(nullable = false, columnDefinition = "VARCHAR(MAX)")
    private String optionContent;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

}