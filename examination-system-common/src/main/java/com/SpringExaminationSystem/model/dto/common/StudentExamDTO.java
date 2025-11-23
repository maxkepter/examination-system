package com.SpringExaminationSystem.model.dto.common;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
    Integer studentExamId;
    Integer examStatus;
    String examStatusInfo;
    float score;
    LocalDateTime submitTime;
    LocalDateTime startTime;
    Integer examId;
    String examName;
}
