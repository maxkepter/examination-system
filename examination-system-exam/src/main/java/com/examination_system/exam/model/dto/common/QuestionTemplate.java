package com.examination_system.exam.model.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionTemplate {
    @NotBlank
    private String chapterNo;

    @NotBlank
    @Pattern(regexp = "^[123]$", message = "Difficulty must be 1, 2, or 3")
    private String difficulty;

    @Positive
    private int amount;

}