package com.examination_system.exam.model.dto.common;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionTemplate {
    @NotNull(message = "Chapter ID is required")
    private Long chapterId;

    @NotNull(message = "Difficulty is required")
    private Integer difficulty;

    @Positive(message = "Amount must be positive")
    private int amount;
}