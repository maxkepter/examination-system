package com.examination_system.exam.model.mapper;

import com.examination_system.common.model.entity.exam.Exam;
import com.examination_system.exam.model.dto.common.ExamDto;
import com.examination_system.exam.model.dto.response.ExamResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { QuestionMapper.class })
public interface ExamMapper {

    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "examId", ignore = true)
    @Mapping(target = "examCode", ignore = true)
    @Mapping(source = "subjectCode", target = "subject.subjectCode")
    Exam toEntity(ExamDto examDto);

    @Mapping(source = "examId", target = "examId")
    @Mapping(source = "questions", target = "questionResponses")
    @Mapping(source = "subject.subjectCode", target = "subjectCode")
    @Mapping(source = "subject.subjectName", target = "subjectName")
    ExamResponse toResponse(Exam exam);

    @Mapping(source = "examId", target = "examId")
    @Mapping(source = "subject.subjectCode", target = "subjectCode")
    @Mapping(target = "questionTemplates", ignore = true)
    ExamDto toDto(Exam exam);
}
