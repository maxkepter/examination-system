package com.SpringExaminationSystem.model.dto.request.exam;

import java.util.Map;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    Map<Integer, Set<Integer>> studentChoice;
}
