package com.examination_system.model.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOptionDTO {
    private Integer optionId; // có thể null khi tạo mới
    private String optionContent;
    @JsonProperty("isCorrect")
    private boolean isCorrect;
}
