package com.SpringExaminationSystem.model.mapper.exam;

import org.mapstruct.Mapper;

import com.SpringExaminationSystem.model.dto.common.MajorDTO;
import com.SpringExaminationSystem.model.entity.exam.Major;

@Mapper(componentModel = "spring")
public interface MajorMapper {
    Major toEntity(MajorDTO request);

    MajorDTO toDto(Major major);
}
