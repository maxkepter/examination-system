package com.examination_system.model.converter;

import java.util.Map;
import java.util.Set;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.ws.rs.core.GenericType;

@Converter
public class StudentChoiceConverter implements AttributeConverter<Map<Long, Set<Long>>, String> {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public String convertToDatabaseColumn(Map<Long, Set<Long>> attribute) {
        return jsonb.toJson(attribute);
    }

    @Override
    public Map<Long, Set<Long>> convertToEntityAttribute(String dbData) {
        return jsonb.fromJson(dbData, new GenericType<Map<Long, Set<Long>>>() {
        }.getType());
    }
}
