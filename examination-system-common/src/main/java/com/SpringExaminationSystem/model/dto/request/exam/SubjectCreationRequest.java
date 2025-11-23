package com.SpringExaminationSystem.model.dto.request.exam;

import java.util.List;

import com.SpringExaminationSystem.model.dto.common.SubjectDTO;

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
public class SubjectCreationRequest extends SubjectDTO {
    List<String> majorCodes;
    @NotEmpty(message = "Chapters are required")
    List<String> chapters;

}