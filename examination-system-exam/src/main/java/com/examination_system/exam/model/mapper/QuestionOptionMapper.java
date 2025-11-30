package com.examination_system.exam.model.mapper;

import com.examination_system.exam.model.dto.common.QuestionOptionDto;
import org.mapstruct.*;

import com.examination_system.common.model.entity.exam.QuestionOption;

@Mapper(componentModel = "spring")
public interface QuestionOptionMapper {

    QuestionOptionDto toDto(QuestionOption option);

    // dùng cho create: bỏ qua id
    @Mapping(target = "optionId", ignore = true)
    @Mapping(target = "question", ignore = true) // tránh map vòng lặp
    QuestionOption toNewEntity(QuestionOptionDto dto);

    // dùng cho update: giữ id
    @Mapping(target = "question", ignore = true)
    void updateEntity(QuestionOptionDto dto, @MappingTarget QuestionOption entity);
}
