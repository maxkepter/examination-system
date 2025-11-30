package com.examination_system.exam.model.dto.common;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ExamDto {
    Long examId;

    @Positive(message = "Duration must be positive")
    int duration;

    @NotNull(message = "Exam date is required")
    @FutureOrPresent(message = "Exam date must be in the future or present")
    LocalDateTime examDate;

    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be in the future")
    LocalDateTime deadline;

    @NotBlank(message = "Exam name is required")
    @Size(max = 100, message = "Exam name must not exceed 100 characters")
    String examName;

    String examCode;

    @NotBlank(message = "Subject code is required")
    String subjectCode;

    @NotNull(message = "Question templates are required")
    @Size(min = 1, message = "At least one question template is required")
    List<QuestionTemplate> questionTemplates;
}