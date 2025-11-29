package com.examination_system.model.mapper.exam;

import org.mapstruct.Mapper;

import com.examination_system.model.dto.common.MajorDTO;
import com.examination_system.model.entity.exam.Major;

@Mapper(componentModel = "spring")
public interface MajorMapper {
    Major toEntity(MajorDTO request);

    MajorDTO toDto(Major major);
}
