package com.examination_system.exam.model.mapper;

import com.examination_system.exam.model.dto.common.SubjectDto;
import com.examination_system.exam.model.dto.request.SubjectCreationRequest;
import com.examination_system.exam.model.dto.response.SubjectRespone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import com.examination_system.model.entity.exam.Subject;
import com.examination_system.model.entity.exam.Major;
import com.examination_system.model.entity.exam.Chapter;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MajorMapper.class, ChapterMapper.class})
public interface SubjectMapper {
    @Mapping(target = "majors", ignore = true)
    @Mapping(target = "chapters", ignore = true)
    Subject toEntity(SubjectDto subject);

    @Mapping(target = "majors", expression = "java(mapMajors(request.getMajorCodes()))")
    @Mapping(target = "chapters", expression = "java(mapChapters(request.getChapters()))")
    Subject toEntity(SubjectCreationRequest request);

    SubjectDto toDto(Subject subject);

    @Mapping(target = "majors", source = "majors")
    @Mapping(target = "chapters", source = "chapters")
    SubjectRespone toResponse(Subject subject);

    default List<Major> mapMajors(List<String> majorCodes) {
        if (majorCodes == null)
            return null;
        return majorCodes.stream()
                .map(code -> Major.builder().majorCode(code).build())
                .collect(Collectors.toList());
    }

    default List<Chapter> mapChapters(List<String> chapterNames) {
        if (chapterNames == null)
            return null;
        return chapterNames.stream()
                .map(name -> Chapter.builder().chapterName(name).build())
                .collect(Collectors.toList());
    }

    @AfterMapping
    default void linkSubjectInChapters(@MappingTarget Subject subject) {
        List<Chapter> chapters = subject.getChapters();
        if (chapters != null) {
            chapters.forEach(ch -> ch.setSubject(subject));
        }
    }
}
