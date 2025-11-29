package com.examination_system.exam.model.dto.common;

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
public class SubjectDto {

    String subjectCode;
    String subjectName;
}
