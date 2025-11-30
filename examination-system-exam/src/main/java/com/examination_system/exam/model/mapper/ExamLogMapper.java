package com.examination_system.exam.model.mapper;

import com.examination_system.common.model.entity.log.ExamLog;
import com.examination_system.exam.model.dto.response.ExamLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamLogMapper {
    
    @Mapping(source = "examLogId", target = "examLogId")
    @Mapping(source = "infomation", target = "information")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "studentExam.studentExamId", target = "studentExamId")
    ExamLogResponse toResponse(ExamLog examLog);
}
