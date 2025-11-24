package com.examination_system.model.mapper.exam;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.examination_system.model.dto.common.SubjectDTO;
import com.examination_system.model.entity.exam.Subject;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target = "majors", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    Subject toEntity(SubjectDTO subject);

    SubjectDTO toDto(Subject subject);
}
