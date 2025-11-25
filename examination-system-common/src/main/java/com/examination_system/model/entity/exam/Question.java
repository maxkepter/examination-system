package com.examination_system.model.entity.exam;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

import com.examination_system.core.model.entity.BaseEntity;
import com.examination_system.model.entity.exam.student.Option;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "Question")
@SQLDelete(sql = "update Question set isActive=0 where questionId=?")
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer questionId;

    @Lob
    @Column(nullable = false, columnDefinition = "VARCHAR(MAX)")
    private String questionContent;

    @Column(nullable = false)
    private int difficulty;

    @ManyToOne
    @JoinColumn(name = "chapterId", nullable = false)
    private Chapter chapter;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOption> options = new ArrayList<>();

    public static List<Option> randomQuestion(List<Option> options2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'randomQuestion'");
    }

}