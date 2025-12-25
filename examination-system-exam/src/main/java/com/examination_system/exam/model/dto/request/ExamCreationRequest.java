package com.examination_system.exam.model.dto.request;

import com.examination_system.exam.model.dto.common.QuestionTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ExamCreationRequest {
    int duration;
    LocalDateTime examDate;
    LocalDateTime deadline;
    String examName;
    String subjectCode;
    List<QuestionTemplate> questionTemplates;
}
