package com.examination_system.exam.model.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class MajorDto {
    @NotBlank
    @Size(max = 50)
    String majorCode;
    @NotBlank
    @Size(max = 255)
    String majorName;
}
