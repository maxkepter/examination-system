package com.SpringExaminationSystem.model.converter;

import java.util.Map;
import java.util.Set;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.ws.rs.core.GenericType;

@Converter
public class StudentChoiceConverter implements AttributeConverter<Map<Integer, Set<Integer>>, String> {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public String convertToDatabaseColumn(Map<Integer, Set<Integer>> attribute) {
        return jsonb.toJson(attribute);
    }

    @Override
    public Map<Integer, Set<Integer>> convertToEntityAttribute(String dbData) {
        return jsonb.fromJson(dbData, new GenericType<Map<Integer, Set<Integer>>>() {
        }.getType());
    }
}
