package com.examination_system.model.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ExamLogDTO {
    @NotBlank(message = "Infomartion can not blank !")
    String infomarion;
    @NotNull(message = "Student Exam Id is required !")
    Long studentExamId;
}
