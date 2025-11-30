package com.examination_system.exam.model.dto.request;

import java.util.Set;

import com.examination_system.exam.model.dto.common.SubjectDto;
import jakarta.validation.constraints.NotEmpty;
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
public class SubjectCreationRequest extends SubjectDto {
    @NotEmpty(message = "At least one major code is required")
    Set<String> majorCodes;
    @NotEmpty(message = "Chapters are required")
    Set<String> chapters;

}