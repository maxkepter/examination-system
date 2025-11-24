package com.SpringExaminationSystem.model.mapper.exam;

import com.SpringExaminationSystem.model.dto.common.QuestionOptionDTO;
import com.SpringExaminationSystem.model.entity.exam.QuestionOption;
import org.mapstruct.*;

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
