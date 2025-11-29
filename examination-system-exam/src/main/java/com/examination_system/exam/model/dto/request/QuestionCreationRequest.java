package com.examination_system.exam.model.dto.request;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class QuestionCreationRequest {
    @NotBlank(message = "Question content is required")
    String questionContent;
    @NotNull(message = "Difficulty is required")
    @Range(min = 1, max = 3, message = "Difficulty must be between 1 and 3")
    int difficulty;
    @NotNull(message = "Chapter ID is required")
    Long chapterId;
    @NotNull(message = "Options are required")
    @Size(min = 2, message = "At least two options are required")
    OptionCreationRequest[] options;
}
