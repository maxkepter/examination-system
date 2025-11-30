package com.examination_system.exam.model.mapper;

import com.examination_system.common.model.entity.exam.Question;
import com.examination_system.common.model.entity.exam.QuestionOption;
import com.examination_system.exam.model.dto.common.QuestionDto;
import com.examination_system.exam.model.dto.response.OptionResponse;
import com.examination_system.exam.model.dto.response.QuestionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {QuestionOptionMapper.class})
public interface QuestionMapper {
    @Mapping(source = "chapterId", target = "chapter.chapterId")
    Question toEntity(QuestionDto question);

    @Mapping(source = "chapter.chapterId", target = "chapterId")
    QuestionDto toDTO(Question question);
    
    // Response mapping methods
    @Mapping(source = "questionId", target = "questionId")
    @Mapping(source = "questionContent", target = "content")
    @Mapping(source = "difficulty", target = "difficulty")
    @Mapping(source = "options", target = "options")
    QuestionResponse toResponse(Question question);
    
    @Mapping(source = "optionId", target = "optionId")
    @Mapping(source = "optionContent", target = "optionContent")
    OptionResponse toOptionResponse(QuestionOption questionOption);
    
    default List<QuestionResponse> toResponseList(List<Question> questions) {
        if (questions == null) {
            return null;
        }
        return questions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
