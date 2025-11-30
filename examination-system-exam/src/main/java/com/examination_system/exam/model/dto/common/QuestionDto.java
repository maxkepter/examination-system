package com.examination_system.exam.model.dto.common;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
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
    @NotBlank
    @Size(max = 5000)
    String questionContent;

    @Range(min = 1, max = 3)
    int difficulty;

    @NotNull
    Long chapterId;

    @NotNull
    @Size(min = 2)
    List<QuestionOptionDTO> options;
}
