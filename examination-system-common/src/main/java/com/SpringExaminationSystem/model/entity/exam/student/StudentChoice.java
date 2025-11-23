package com.SpringExaminationSystem.model.entity.exam.student;

import jakarta.json.bind.annotation.JsonbProperty;

public class StudentChoice {
    @JsonbProperty("questionId")
    private int questionId;
    @JsonbProperty("optionId")
    private int optionId;
    @JsonbProperty("isChecked")
    private int isChecked;
}