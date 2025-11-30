package com.examination_system.exam.model.mapper;

import java.util.List;

import com.examination_system.common.model.entity.exam.student.StudentExam;
import com.examination_system.exam.model.dto.common.StudentExamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.examination_system.exam.model.dto.response.StudentExamResponse;


@Mapper(componentModel = "spring")
public interface StudentExamMapper {

    @Mapping(target = "examId", source = "exam.examId")
    @Mapping(target = "examName", source = "exam.examName")
    @Mapping(target = "examCode", source = "exam.examCode")
    @Mapping(target = "deadline", source = "exam.deadline")
    @Mapping(target = "duration", source = "exam.duration")
    @Mapping(target = "examStatusInfo", expression = "java(StudentExam.EXAM_STATUS_INFO[entity.getExamStatus()])")
    StudentExamDto toDto(StudentExam entity);

    @Mapping(target = "examId", source = "exam.examId")
    @Mapping(target = "examName", source = "exam.examName")
    @Mapping(target = "examCode", source = "exam.examCode")
    @Mapping(target = "deadline", source = "exam.deadline")
    @Mapping(target = "duration", source = "exam.duration")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "userName", expression = "java(entity.getUser()!=null && entity.getUser().getAuthInfo()!=null ? entity.getUser().getAuthInfo().getUserName() : null)")
    @Mapping(target = "examStatusInfo", expression = "java(StudentExam.EXAM_STATUS_INFO[entity.getExamStatus()])")
    StudentExamResponse toResponse(StudentExam entity);

    List<StudentExamDto> toDtos(List<StudentExam> list);
}
