package com.SpringExaminationSystem.model.entity.exam.student;

import java.util.List;

import jakarta.json.bind.annotation.JsonbProperty;

public class ExamChoice {
    @JsonbProperty("studentExamId")
    private int studentExamId;
    @JsonbProperty("answers")
    private List<StudentChoice> studentChoices;
}