package com.examination_system.model.dto.response.exam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.examination_system.model.dto.common.StudentExamDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentExamResponse extends StudentExamDTO {

    List<QuestionResponse> examDetail;
    Map<Long, Set<Long>> studentChoice;
    Long userId;
    String userName;

}
