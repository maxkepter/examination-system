package com.examination_system.exam.model.dto.request;

import java.util.List;

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
    List<String> majorCodes;
    @NotEmpty(message = "Chapters are required")
    List<String> chapters;

}