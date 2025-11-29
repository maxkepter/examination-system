package com.examination_system.model.dto.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class QuestionDTO {
    Long questionId;
    String questionContent;
    int difficulty;
    Long chapterId;
    List<QuestionOptionDTO> options;
}
