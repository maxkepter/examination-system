package com.examination_system.common.model.entity.exam.student;

import jakarta.json.bind.annotation.JsonbProperty;

public class StudentChoice {
    @JsonbProperty("questionId")
    private long questionId;
    @JsonbProperty("optionId")
    private long optionId;
    @JsonbProperty("isChecked")
    private int isChecked;
}