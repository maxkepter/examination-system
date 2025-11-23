package com.SpringExaminationSystem.model.mapper.exam;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.SpringExaminationSystem.model.dto.common.StudentExamDTO;
import com.SpringExaminationSystem.model.dto.response.exam.StudentExamResponse;
import com.SpringExaminationSystem.model.entity.exam.student.StudentExam;

@Mapper(componentModel = "spring")
public interface StudentExamMapper {

    @Mapping(target = "examId", source = "exam.examId")
    @Mapping(target = "examName", source = "exam.examName")
    @Mapping(target = "examStatusInfo", expression = "java(StudentExam.EXAM_STATUS_INFO[entity.getExamStatus()])")
    StudentExamDTO toDto(StudentExam entity);

    @Mapping(target = "examId", source = "exam.examId")
    @Mapping(target = "examName", source = "exam.examName")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "userName", expression = "java(entity.getUser()!=null && entity.getUser().getAuthInfo()!=null ? entity.getUser().getAuthInfo().getUserName() : null)")
    @Mapping(target = "examStatusInfo", expression = "java(StudentExam.EXAM_STATUS_INFO[entity.getExamStatus()])")
    StudentExamResponse toResponse(StudentExam entity);

    List<StudentExamDTO> toDtos(List<StudentExam> list);
}
