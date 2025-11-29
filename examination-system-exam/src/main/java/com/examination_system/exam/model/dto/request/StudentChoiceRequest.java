package com.examination_system.exam.model.dto.request;

import java.util.Map;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentChoiceRequest {
    @NotNull(message = "isRemove required !")
    boolean isRemove;
    @NotNull
    @NotEmpty
    Map<Long, Set<Long>> studentChoice;
}
