package com.SpringExaminationSystem.model.dto.request.exam;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class QuestionCreationRequest {
    @NotNull(message = "Question content is required")
    String questionContent;
    @NotNull(message = "Difficulty is required")
    @Range(min = 1, max = 3, message = "Difficulty must be between 1 and 3")
    int difficulty;
    @NotNull(message = "Chapter ID is required")
    Integer chapterId;
    @NotNull(message = "Options are required")
    OptionCreationRequest[] options;
}
