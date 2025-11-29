package com.examination_system.exam.model.mapper;

import com.examination_system.exam.model.dto.common.QuestionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.examination_system.model.entity.exam.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(source = "chapterId", target = "chapter.chapterId")
    Question toEntity(QuestionDTO question);

    @Mapping(source = "chapter.chapterId", target = "chapterId")
    QuestionDTO toDTO(Question question);

}
