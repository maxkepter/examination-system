package com.examination_system.exam.model.dto.common;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentExamDTO {
    Long studentExamId;
    @NotNull
    Integer examStatus;
    String examStatusInfo;
    @PositiveOrZero
    float score;
    LocalDateTime submitTime;
    LocalDateTime startTime;
    @NotNull
    Long examId;
    @Size(max = 255)
    String examName;
}
