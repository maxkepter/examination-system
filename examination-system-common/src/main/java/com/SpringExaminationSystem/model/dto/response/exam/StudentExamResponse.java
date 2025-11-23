package com.SpringExaminationSystem.model.dto.response.exam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.SpringExaminationSystem.model.dto.common.StudentExamDTO;
import com.SpringExaminationSystem.model.entity.exam.student.QuestionWithOptions;
import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;

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
    Map<Integer, Set<Integer>> studentChoice;
    Integer userId;
    String userName;

}
