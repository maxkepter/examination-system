package com.examination_system.exam.model.mapper;

import org.mapstruct.*;

import com.examination_system.exam.model.dto.common.QuestionOptionDTO;
import com.examination_system.common.model.entity.exam.QuestionOption;

@Mapper(componentModel = "spring")
public interface QuestionOptionMapper {

    QuestionOptionDTO toDto(QuestionOption option);

    // dùng cho create: bỏ qua id
    @Mapping(target = "optionId", ignore = true)
    @Mapping(target = "question", ignore = true) // tránh map vòng lặp
    QuestionOption toNewEntity(QuestionOptionDTO dto);

    // dùng cho update: giữ id
    @Mapping(target = "question", ignore = true)
    void updateEntity(QuestionOptionDTO dto, @MappingTarget QuestionOption entity);
}
