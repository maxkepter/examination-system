package com.examination_system.model.entity.exam.student;

import java.util.List;

import jakarta.json.bind.annotation.JsonbProperty;

public class ExamChoice {
    @JsonbProperty("studentExamId")
    private long studentExamId;
    @JsonbProperty("answers")
    private List<StudentChoice> studentChoices;
}