package com.examination_system.exam.model.dto.request;

import com.examination_system.exam.model.dto.common.QuestionTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ExamCreationRequest {
    int duration;
    LocalDate examDate;
    LocalDate deadline;
    String examName;
    List<QuestionTemplate> questionTemplates;
}
