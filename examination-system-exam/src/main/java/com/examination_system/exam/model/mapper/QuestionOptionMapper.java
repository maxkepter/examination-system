package com.examination_system.exam.model.mapper;

import com.examination_system.exam.model.dto.common.QuestionOptionDto;
import org.mapstruct.*;

import com.examination_system.common.model.entity.exam.QuestionOption;

@Mapper(componentModel = "spring")
public interface QuestionOptionMapper {

    default QuestionOptionDto toDto(QuestionOption option) {
        if (option == null) {
            return null;
        }
        return QuestionOptionDto.builder()
                .optionId(option.getOptionId())
                .optionContent(option.getOptionContent())
                .isCorrect(option.isCorrect())
                .build();
    }

    // dùng cho create: bỏ qua id
    default QuestionOption toNewEntity(QuestionOptionDto dto) {
        if (dto == null) {
            return null;
        }
        QuestionOption option = new QuestionOption();
        option.setOptionContent(dto.getOptionContent());
        option.setCorrect(dto.getIsCorrect());
        return option;
    }

    // dùng cho update: giữ id
    default void updateEntity(QuestionOptionDto dto, @MappingTarget QuestionOption entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setOptionContent(dto.getOptionContent());
        entity.setCorrect(dto.getIsCorrect());
    }
}
