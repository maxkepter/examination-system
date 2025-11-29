package com.examination_system.exam.model.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class MajorDto {
    String majorCode;
    String majorName;
}
