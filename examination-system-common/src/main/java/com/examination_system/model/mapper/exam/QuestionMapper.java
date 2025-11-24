package com.examination_system.model.mapper.exam;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.examination_system.model.dto.common.QuestionDTO;
import com.examination_system.model.entity.exam.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(source = "chapterId", target = "chapter.chapterId")
    Question toEntity(QuestionDTO question);

    @Mapping(source = "chapter.chapterId", target = "chapterId")
    QuestionDTO toDTO(Question question);

}
