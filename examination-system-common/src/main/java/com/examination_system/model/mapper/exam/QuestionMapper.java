package com.SpringExaminationSystem.model.mapper.exam;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.SpringExaminationSystem.model.dto.common.QuestionDTO;
import com.SpringExaminationSystem.model.entity.exam.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(source = "chapterId", target = "chapter.chapterId")
    Question toEntity(QuestionDTO question);

    @Mapping(source = "chapter.chapterId", target = "chapterId")
    QuestionDTO toDTO(Question question);

    
}
