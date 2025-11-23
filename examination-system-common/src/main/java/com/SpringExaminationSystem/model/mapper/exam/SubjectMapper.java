package com.SpringExaminationSystem.model.mapper.exam;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.SpringExaminationSystem.model.dto.common.SubjectDTO;
import com.SpringExaminationSystem.model.entity.exam.Subject;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target = "majors", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    Subject toEntity(SubjectDTO subject);

    SubjectDTO toDto(Subject subject);
}
