package com.examination_system.exam.model.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOptionDTO {
    private Long optionId; // có thể null khi tạo mới
    @NotBlank
    @Size(max = 2000)
    private String optionContent;
    @JsonProperty("isCorrect")
    @NotNull
    private boolean isCorrect;
}
