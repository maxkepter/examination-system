package com.examination_system.exam.model.mapper;

import com.examination_system.exam.model.dto.common.ChapterDto;
import com.examination_system.common.model.entity.exam.Chapter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    ChapterDto toDto(Chapter chapter);
}
