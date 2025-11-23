package com.SpringExaminationSystem.model.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class MajorDTO {
    String majorCode;
    String majorName;
}
