package com.SpringExaminationSystem.model.dto.request.exam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OptionCreationRequest {
    @NotBlank(message = "Option content is required")
    String optionContent;
    @NotNull(message = "Is correct is required")
    boolean isCorrect;

}
