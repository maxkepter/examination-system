package com.examination_system.exam.model.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOptionDTO {
    private Long optionId; // có thể null khi tạo mới
    private String optionContent;
    @JsonProperty("isCorrect")
    private boolean isCorrect;
}
