package com.examination_system.exam.model.dto.response;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.examination_system.exam.model.dto.common.StudentExamDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class StudentExamResponse extends StudentExamDto {

    @NotNull
    @Size(min = 1)
    List<QuestionResponse> examDetail;

    @NotNull
    Map<Long, Set<Long>> studentChoice;
    Long userId;
    String userName;

}
