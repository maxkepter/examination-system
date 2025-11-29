package com.examination_system.exam.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExamCreationRequest {

    @NotNull(message = "Exam ID is required")
    private Long examId;
}
