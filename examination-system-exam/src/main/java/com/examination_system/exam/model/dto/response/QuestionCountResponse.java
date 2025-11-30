package com.examination_system.exam.model.dto.response;

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
public class QuestionCountResponse {
    String subjectCode;
    String subjectName;
    Long chapterId;
    String chapterName;
    Integer difficulty;
    long availableQuestions;
}
