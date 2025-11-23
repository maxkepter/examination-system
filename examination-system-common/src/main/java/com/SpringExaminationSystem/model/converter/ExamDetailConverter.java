package com.SpringExaminationSystem.model.converter;

import java.util.List;

import com.SpringExaminationSystem.model.entity.exam.student.QuestionWithOptions;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.ws.rs.core.GenericType;

@Converter
public class ExamDetailConverter implements AttributeConverter<List<QuestionWithOptions>, String> {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public String convertToDatabaseColumn(List<QuestionWithOptions> attribute) {
        return jsonb.toJson(attribute);
    }

    @Override
    public List<QuestionWithOptions> convertToEntityAttribute(String dbData) {
        return jsonb.fromJson(dbData, new GenericType<List<QuestionWithOptions>>() {
        }.getType());
    }
}
