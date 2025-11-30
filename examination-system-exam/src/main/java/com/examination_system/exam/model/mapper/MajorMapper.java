package com.examination_system.exam.model.mapper;

import org.mapstruct.Mapper;

import com.examination_system.exam.model.dto.common.MajorDto;
import com.examination_system.common.model.entity.exam.Major;

@Mapper(componentModel = "spring")
public interface MajorMapper {
    Major toEntity(MajorDto request);

    MajorDto toDto(Major major);
}
